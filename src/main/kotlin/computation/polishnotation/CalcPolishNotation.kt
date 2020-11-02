package computation.polishnotation

import computation.polishnotation.extensions.isOperandOrTempVariable
import models.exceptions.computorv2.calcexception.IllegalTokenException
import models.exceptions.computorv2.calcexception.TooFewOperatorsException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import globalextensions.tryCastToInt
import models.math.dataset.DataSet
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import models.tempVariables
import models.variables
import parser.variable.numeric.toSetNumber
import java.util.*
import kotlin.math.pow

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
			throw IllegalTokenException()
		}

		val newNum = when (element) {
			"+" -> firstElement + secondElement
			"-" -> firstElement - secondElement
			"/" -> firstElement / secondElement
			"*" -> firstElement * secondElement
			"%" -> firstElement % secondElement
			"^" -> {
				if (firstElement !is Numeric || secondElement !is SetNumber)
					throw IllegalOperationException(firstElement::class, secondElement::class, '^')

				if (firstElement is SetNumber) {
					SetNumber(
						(firstElement.number.toDouble().pow(secondElement.number.toDouble())).tryCastToInt()
					)
				} else {
					if (secondElement.number is Double || secondElement < 0)
						throw IllegalOperationException(firstElement::class, secondElement::class, '^')
					for (i in 1 until secondElement.number as Int)
						firstElement *= firstElement
					firstElement
				}
			}
			else -> throw IllegalTokenException(element)
		}

		stack.push(newNum)
	}

	if (stack.size != 1) throw TooFewOperatorsException()

	return stack.pop()
}