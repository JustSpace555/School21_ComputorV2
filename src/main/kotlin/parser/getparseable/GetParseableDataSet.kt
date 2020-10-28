package parser.getparseable

import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import parser.variable.numeric.isComplex
import kotlin.reflect.KClass

fun getParseableDataSet(input: List<String>): KClass<*> {
	val equalPosition = input.indexOf("=")
	val beforeEqual = input.subList(0, equalPosition)
	val afterEqual = input.subList(equalPosition + 1, input.lastIndex + 1)

	return when {
		checkIfFunction(beforeEqual) -> Function::class
		checkIfMatrix(afterEqual) -> Matrix::class

		afterEqual.any { it.isComplex() } -> Complex::class

		else -> SetNumber::class
	}
}