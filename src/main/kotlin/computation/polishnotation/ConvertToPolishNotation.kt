package computation.polishnotation

import computation.polishnotation.extensions.getOperandLastIndex
import computation.polishnotation.extensions.isComplexOrMatrixOrFunction
import computation.polishnotation.extensions.isOperand
import models.exception.calcexception.BracketsAmountException
import models.exception.calcexception.IllegalTokenException
import models.math.tempVariables
import java.util.*

private fun choosePriority(input: String): Int =
	when (input) {
		"^" -> 4
		"*", "/", "%" -> 3
		"+", "-" -> 2
		"(" -> 1
		else -> throw IllegalTokenException(input)
	}

fun convertToPolishNotation(input: List<String>): List<String> {
	val output = mutableListOf<String>()
	val stack = Stack<String>()

	var i = 0
	while (i in input.indices) {
		if (input[i].isOperand()) {
			output.add(input[i++])
			continue
		}

		val checkingOperand = input[i].isComplexOrMatrixOrFunction()
		if (checkingOperand.first) {
			val lastIndexToSlice = input.getOperandLastIndex(checkingOperand.second)

			"var_${tempVariables.size + 1}".also {
				tempVariables[it] = Pair(input.subList(i, i + lastIndexToSlice), checkingOperand.second)
				output.add(it)
			}
			i += lastIndexToSlice
			continue
		}

		if (stack.isEmpty()) {
			stack.push(input[i++])
			continue
		}

		if (input[i] == ")") {
			while (stack.isNotEmpty() && stack.peek() != "(")
				output.add(stack.pop())

			if (stack.isEmpty())
				throw BracketsAmountException()

			stack.pop()
			i++
			continue
		} else {
			val elementPriority = choosePriority(input[i])
			while (!stack.isEmpty() && choosePriority(stack.peek()) >= elementPriority && elementPriority > 1)
				output.add(stack.pop())

			stack.push(input[i++])
		}
	}

	while (stack.isNotEmpty())
		output.add(stack.pop())
	return output
}