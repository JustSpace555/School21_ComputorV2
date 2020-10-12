package parser.getparseable

import models.dataset.Complex
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.SetNumber
import kotlin.reflect.KClass

fun getParseableDataSet(input: List<String>): KClass<*> {
	val equalPosition = input.indexOf("=")
	val beforeEqual = input.subList(0, equalPosition)
	val afterEqual = input.subList(equalPosition + 1, input.lastIndex + 1)

	if (checkIfFunction(beforeEqual))
		return Function::class

	if (afterEqual.any { it.contains(Regex("\\d*[iI]")) })
		return Complex::class

	if (afterEqual.contains(";") && afterEqual.any { it.contains("[[") } &&
		afterEqual.any { it.contains("]]") }) {
		return Matrix::class
	}

	return SetNumber::class
}