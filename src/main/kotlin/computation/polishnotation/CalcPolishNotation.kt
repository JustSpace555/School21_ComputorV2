package computation.polishnotation

import computation.polishnotation.extensions.isOperandOrTempVariable
import models.exceptions.computorv2.calcexception.IllegalTokenException
import models.exceptions.computorv2.calcexception.TooFewOperatorsException
import models.math.dataset.DataSet
import models.tempVariables
import models.variables
import parser.variable.numeric.toSetNumber
import java.util.*

fun calcPolishNotation(input: List<String>): DataSet {
	val stack = Stack<DataSet>()

	for (element in input) {
		if (element.isOperandOrTempVariable()) {
			val dataSetElement = when {
				variables.containsKey(element) -> variables[element]
				tempVariables.containsKey(element) -> tempVariables[element]
				else -> element.toSetNumber()
			}

			stack.push(dataSetElement)
			continue
		}

		lateinit var firstElement: DataSet
		lateinit var secondElement: DataSet
		try {
			secondElement = stack.pop()
			firstElement = stack.pop()
		} catch (e: EmptyStackException) {
			throw IllegalTokenException(element)
		}

		val newNum = when (element) {
			"+" -> firstElement + secondElement
			"-" -> firstElement - secondElement
			"/" -> firstElement / secondElement
			"*" -> firstElement * secondElement
			"%" -> firstElement % secondElement
			"^" -> firstElement.pow(secondElement)
			else -> throw IllegalTokenException(element)
		}

		stack.push(newNum)
	}

	if (stack.size != 1) throw TooFewOperatorsException()

	return stack.pop()
}