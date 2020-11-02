package parser.getparseable

import models.exceptions.computorv2.parserexception.variable.MultipleArgumentException
import models.exceptions.computorv2.parserexception.variable.WrongFunctionBracketsFormatException

private fun List<String>.countInARow(input: String): Pair<Boolean, Int> {
	val firstIndex = this.indexOf(input)
	val lastIndex = this.lastIndexOf(input)

	for (i in firstIndex..lastIndex) {
		if (this[i] != input)
			return Pair(false, 0)
	}
	return Pair(true, lastIndex - firstIndex + 1)
}

private fun List<String>.countArguments(): Int = this.slice(1..lastIndex).filter { it != "(" && it != ")" }.size

fun checkIfFunction(beforeEqual: List<String>): Boolean {
	if (beforeEqual.size == 1)
		return false

	if (beforeEqual.countArguments() > 1)
		throw MultipleArgumentException()

	val indexOfOpenBracket = beforeEqual.lastIndexOf("(")
	val indexOfCloseBracket = beforeEqual.indexOf(")")
	if (indexOfCloseBracket == -1 && indexOfOpenBracket == -1) return false
	else if (!(indexOfOpenBracket != -1 && indexOfCloseBracket != -1)) {
		throw WrongFunctionBracketsFormatException()
	}

	val countItARowOpenBracket = beforeEqual.countInARow("(")
	val countInARowCloseBracket = beforeEqual.countInARow(")")
	if (!(countItARowOpenBracket.first && countInARowCloseBracket.first) ||
		countItARowOpenBracket.second != countInARowCloseBracket.second) {
		throw WrongFunctionBracketsFormatException()
	}

	if (indexOfOpenBracket + 2 != indexOfCloseBracket)
		throw WrongFunctionBracketsFormatException()

	return true
}