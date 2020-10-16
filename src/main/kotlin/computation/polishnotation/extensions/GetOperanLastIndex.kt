package computation.polishnotation.extensions

import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import java.util.Stack
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

	return i
}

fun List<String>.getOperandLastIndex(inputClass: KClass<*>): Int =
	when (inputClass) {
		Complex::class -> 1
		Matrix::class -> getMatrixLastIndex(this)
		Function::class -> 4
		else -> 1
	}