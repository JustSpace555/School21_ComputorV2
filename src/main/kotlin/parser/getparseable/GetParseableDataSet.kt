package parser.getparseable

import models.exceptions.computorv2.parserexception.variable.IllegalFunctionElement
import models.dataset.function.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import parser.variable.numeric.isComplex
import kotlin.reflect.KClass

fun getParseableDataSet(input: List<String>): KClass<*> {
	val equalPosition = input.indexOf("=")
	val beforeEqual = input.subList(0, equalPosition)
	val afterEqual = input.subList(equalPosition + 1, input.lastIndex + 1)

	return when {
		checkIfFunction(beforeEqual) -> {
			if (checkIfMatrix(afterEqual)) throw IllegalFunctionElement()
			Function::class
		}

		checkIfMatrix(afterEqual) -> Matrix::class

		afterEqual.any { it.isComplex() } -> Complex::class

		else -> SetNumber::class
	}
}