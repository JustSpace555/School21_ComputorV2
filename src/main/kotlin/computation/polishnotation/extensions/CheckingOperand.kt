package computation.polishnotation.extensions

import models.exception.parserexception.variable.NoSuchVariableException
import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import models.math.tempVariables
import models.math.variables
import kotlin.reflect.KClass

fun String.isOperand(): Boolean = this.toDoubleOrNull() != null || variables.containsKey(this)

fun String.isOperandOrTempVariable(): Boolean = this.isOperand() || tempVariables.containsKey(this)

fun String.isComplexOrMatrixOrFunction(): Pair<Boolean, KClass<*>> =
	when {
		this.contains('i') -> {
			if (!this.matches(Regex("\\d*i"))) throw NoSuchVariableException()
			Pair(true, Complex::class)
		}

		this == "[" -> Pair(true, Matrix::class)

		variables.containsKey(this) && variables[this] is Function -> Pair(true, Function::class)

		else -> Pair(false, SetNumber::class)
	}