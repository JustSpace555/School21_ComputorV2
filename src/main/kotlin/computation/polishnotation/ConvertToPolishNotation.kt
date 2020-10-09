package computation.polishnotation

import models.exception.calcexception.BracketsAmountException
import models.exception.calcexception.IllegalTokenException
import java.util.*

fun choosePriority(input: String): Int =
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

	for (element in input) {
		if (element.toDoubleOrNull() != null) {
			output.add(element)
			continue
		}

		if (stack.isEmpty()) {
			stack.push(element)
			continue
		}

		if (element == ")") {
			while (!stack.isEmpty() && stack.peek() != "(")
				output.add(stack.pop())

			if (stack.isEmpty())
				throw BracketsAmountException()

			stack.pop()
			continue
		} else {
			val elementPriority = choosePriority(element)
			while (!stack.isEmpty() && choosePriority(stack.peek()) >= elementPriority && elementPriority > 1)
				output.add(stack.pop())

			stack.push(element)
		}
	}

	while (!stack.isEmpty())
		output.add(stack.pop())
	return output
}