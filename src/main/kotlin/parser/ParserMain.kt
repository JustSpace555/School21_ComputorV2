package parser

import models.exception.parserexception.equalsign.EqualAmountException
import models.exception.parserexception.equalsign.EqualPositionException
import models.math.MathExpression
import models.math.calculation.Calculation
import parser.extensions.putSpaces
import parser.extensions.validateVariable
import parser.getparseable.getParseableDataSet

fun parser(input: String): MathExpression {
	val mod = putSpaces(input).split(' ').filter { it.isNotEmpty() }
	val indexOfEqual = mod.indexOf("=")

	if (indexOfEqual != mod.lastIndexOf("="))
		throw EqualAmountException()
	else if (indexOfEqual == 0 || indexOfEqual == mod.lastIndex)
		throw EqualPositionException()

	if (indexOfEqual == -1 || mod.contains("?")) {
		return Calculation(mod.filter { it != "?" && it != "=" })
	}

	val parseableKClass = getParseableDataSet(mod)
	validateVariable(mod.subList(0, indexOfEqual), parseableKClass)

	TODO()
}