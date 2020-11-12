package computation.polishnotation.extensions

import models.exceptions.computorv2.calcexception.BracketsAmountException
import models.math.dataset.Function
import models.math.dataset.Matrix
import java.util.*
import kotlin.reflect.KClass

private fun getMatrixLastIndex(input: List<String>): Int {
	val stack = Stack<Char>()
	var i = 0

	input.forEach (fun(element: String) {
		if (element == "[") stack.push('[')
		else if (element == "]") stack.pop()
		i++

		if (stack.isEmpty())
			return
	})

	return i - 1
}

private fun getFunctionLastIndex(input: List<String>): Int {
	val stackOfBrackets: Stack<String> = Stack()
	var index = 1
	for (element in input.subList(1, input.size)){
		if (element == "(") {
			stackOfBrackets.push(element)
		} else if (element == ")") {
			if (stackOfBrackets.isEmpty()) throw BracketsAmountException()
			stackOfBrackets.pop()
		}

		index++
		if (stackOfBrackets.isEmpty()) break
	}

	return index
}

fun List<String>.getOperandLastIndex(inputClass: KClass<*>): Int =
	when (inputClass) {
		Matrix::class -> getMatrixLastIndex(this)
		Function::class -> getFunctionLastIndex(this)
		else -> 1
	}