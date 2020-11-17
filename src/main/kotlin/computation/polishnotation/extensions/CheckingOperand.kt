package computation.polishnotation.extensions

import computorv1.models.PolynomialTerm
import models.exceptions.computorv2.calcexception.variable.NoSuchVariableException
import models.dataset.Matrix
import models.dataset.Function
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.tempVariables
import models.variables
import parser.variable.numeric.isComplex
import kotlin.reflect.KClass

fun String.isOperand(): Boolean = this.toDoubleOrNull() != null || variables.containsKey(this)

fun String.isOperandOrTempVariable(): Boolean = this.isOperand() || tempVariables.containsKey(this)

fun String.isComplexOrMatrixOrFunctionOrParameter(parameter: String = ""): Pair<Boolean, KClass<*>> =
	when {
		this.isComplex() -> {
			if (!this.isComplex()) throw NoSuchVariableException()
			Pair(true, Complex::class)
		}

		this == "[" -> Pair(true, Matrix::class)

		variables.containsKey(this) && variables[this] is Function -> Pair(true, Function::class)

		this == parameter -> Pair(true, PolynomialTerm::class)

		else -> Pair(false, SetNumber::class)
	}