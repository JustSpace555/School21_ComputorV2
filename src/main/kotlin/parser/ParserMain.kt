package parser

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.parserexception.equalsign.EqualAmountException
import models.exception.parserexception.equalsign.EqualPositionException
import models.math.MathExpression
import models.math.calculation.Calculation
import parser.extensions.putSpaces
import parser.extensions.validateVariable
import parser.getparseable.getParseableDataSet

fun parser(input: String): Pair<String, MathExpression> {
	val mod = putSpaces(input).split(' ').filter { it.isNotEmpty() }
	val indexOfEqual = mod.indexOf("=")

	if (indexOfEqual != mod.lastIndexOf("="))
		throw EqualAmountException()
	else if (indexOfEqual == 0 || indexOfEqual == mod.lastIndex)
		throw EqualPositionException()

	if (indexOfEqual == -1 || mod.contains("?")) {
		return Pair("", Calculation(mod.filter { it != "?" && it != "=" }))
	}

	val beforeEqual = mod.subList(0, indexOfEqual)
	val afterEqual = mod.subList(indexOfEqual + 1, mod.lastIndex)
	val parseableKClass = getParseableDataSet(mod)
	validateVariable(beforeEqual, parseableKClass)

	//TODO поддержка множественных элементов до знака =
	return Pair(beforeEqual.first(), calcPolishNotation(convertToPolishNotation(afterEqual)))
}