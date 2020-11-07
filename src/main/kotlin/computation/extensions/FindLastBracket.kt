package computation.extensions

import models.exceptions.computorv2.calcexception.BracketsAmountException
import java.util.*

fun List<String>.findLastBracket(): Int {
	val stackOfBrackets = Stack<String>()
	var i: Int = -1

	forEachIndexed(fun(index: Int, str: String) {
		if (str == "(") stackOfBrackets.push(str)
		if (str == ")") {
			if (stackOfBrackets.isEmpty()) throw BracketsAmountException()
			stackOfBrackets.pop()
			if (stackOfBrackets.isEmpty()) {
				i = index
				return
			}
		}
	})

	if (i == -1) throw BracketsAmountException()

	return i
}