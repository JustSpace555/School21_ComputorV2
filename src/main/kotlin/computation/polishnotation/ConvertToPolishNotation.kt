package computation.polishnotation

import models.exception.calcexception.BracketsAmountException
import models.exception.calcexception.IllegalTokenException
import models.math.tempVariables
import models.math.variables
import java.util.*

private fun choosePriority(input: String): Int =
	when (input) {
		"^" -> 4
		"*", "/", "%" -> 3
		"+", "-" -> 2
		"(" -> 1
		else -> throw IllegalTokenException(input)
	}

private fun String.isOperand(): Boolean {
	val containsKey = variables.containsKey(this)

	return this.toDoubleOrNull() != null || containsKey || (this.contains('i') && !containsKey)
}

private fun putTempVariableAndGetName(input: String): String {
	tempVariables["var_${tempVariables.size + 1}"] = input
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