package parser

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.parserexception.equalsign.EqualAmountException
import models.exception.parserexception.equalsign.EqualPositionException

fun parser(input: String) {
	val mod = putSpaces(input).split(' ').filter { it.isNotEmpty() }
	val indexOfEqual = mod.indexOf("=")

	if (indexOfEqual != mod.lastIndexOf("="))
		throw EqualAmountException()
	else if (indexOfEqual == 0 || indexOfEqual == mod.lastIndex)
		throw EqualPositionException()

	if (indexOfEqual == -1 || indexOfEqual == mod.lastIndex - 1 && mod.last() == "?") {
		println(calcPolishNotation(convertToPolishNotation(mod)))
	}
}