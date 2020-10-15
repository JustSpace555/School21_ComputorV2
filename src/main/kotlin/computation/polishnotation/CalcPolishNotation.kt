package computation.polishnotation

import models.exception.calcexception.BracketsAmountException
import models.exception.calcexception.DivideByZeroException
import models.exception.calcexception.IllegalTokenException
import models.exception.calcexception.TooFewOperatorsException
import models.math.dataset.numeric.SetNumber
import java.util.*
import kotlin.math.pow

fun calcPolishNotation(input: List<String>): SetNumber {
	val stack = Stack<Double>()

	for (element in input) {
		if (element.toDoubleOrNull() != null) {
			stack.push(element.toDouble())
			continue
		}

		if (stack.empty()) throw IllegalTokenException()
		val secondNum = stack.pop()

		if (stack.empty()) throw IllegalTokenException()
		val firstNum = stack.pop()

		val newNum = when (element) {
			"+" -> firstNum + secondNum
			"-" -> firstNum - secondNum
			"/" -> {
				if (secondNum == 0.0) throw DivideByZeroException()
				firstNum / secondNum
			}
			"*" -> firstNum * secondNum
			"%" -> firstNum % secondNum
			"^" -> firstNum.pow(secondNum)
			"(" -> throw BracketsAmountException()
			else -> throw IllegalTokenException(element)
		}

		stack.push(newNum)
	}

	if (stack.size != 1) throw TooFewOperatorsException()

	val number = stack.pop()
	return SetNumber(
		if (number.toDouble() - number.toInt() == 0.0) number.toInt() else number.toDouble()
	)
}