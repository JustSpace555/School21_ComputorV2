package computation.polishnotation

import computation.polishnotation.extensions.isOperandOrTempVariable
import models.exception.calcexception.DivideByZeroException
import models.exception.calcexception.IllegalTokenException
import models.exception.calcexception.TooFewOperatorsException
import models.exception.calcexception.variable.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import models.math.tempVariables
import models.math.variables
import parser.variable.numeric.toComplex
import parser.variable.numeric.toSetNumber
import parser.variable.parseAndInvokeFunctionFromListString
import java.util.*
import kotlin.math.pow

fun calcPolishNotation(input: List<String>): DataSet {
	val stack = Stack<DataSet>()

	for (element in input) {
		if (element.isOperandOrTempVariable()) {
			val dataSetElement = when {

				variables.containsKey(element) -> variables[element]

				tempVariables.containsKey(element) -> {
					val removedPair = tempVariables.remove(element) ?: throw RuntimeException()
					when (removedPair.second) {
						Complex::class -> removedPair.first.first().toComplex()
						Function::class -> removedPair.first.parseAndInvokeFunctionFromListString()
						else -> Matrix(removedPair.first.toTypedArray())
					}
				}

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
			"/" -> {
				if (secondElement is SetNumber && secondElement.compareTo(0) == secondElement.number)
					throw DivideByZeroException()
				firstElement / secondElement
			}
			"*" -> firstElement * secondElement
			"%" -> firstElement % secondElement
			"^" -> {
				if (firstElement !is Numeric || secondElement !is SetNumber)
					throw IllegalOperationException(firstElement::class, secondElement::class, '^')

				if (firstElement is SetNumber) {
					SetNumber(firstElement.number.toDouble().pow(secondElement.number.toDouble()))
				} else {
					if (secondElement.number is Double)
						throw IllegalOperationException(firstElement::class, secondElement::class, '^')
					for (i in 1..secondElement.number as Int)
						firstElement *= secondElement
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