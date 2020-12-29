package computation.polishnotation.extensions

import computorv1.models.PolynomialTerm
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.variable.NoSuchVariableException
import models.exceptions.computorv2.parserexception.variable.NumericFormatException
import models.reservedFunctionNames
import models.tempVariables
import models.variables
import parser.variable.numeric.isComplex
import parser.variable.numeric.toComplex
import kotlin.reflect.KClass

fun String.isOperand(): Boolean = this.toDoubleOrNull() != null || variables.containsKey(this)

fun String.isOperandOrTempVariable(): Boolean = this.isOperand() || tempVariables.containsKey(this)

fun String.isComplexOrMatrixOrFunctionOrParameter(parameter: String = ""): Pair<Boolean, KClass<*>> =
	when {
		variables.containsKey(this) && variables[this] is Function || this in reservedFunctionNames -> {
			Pair(true, Function::class)
		}

		variables.containsKey(this) -> Pair(false, variables[this]!!::class)

		this.isComplex() -> {
			try {
				this.toComplex()
			} catch (e: NumericFormatException) {
				throw NoSuchVariableException(this)
			}
			Pair(true, Complex::class)
		}

		this == "[" -> Pair(true, Matrix::class)

		this == parameter -> Pair(true, PolynomialTerm::class)

		else -> Pair(false, SetNumber::class)
	}