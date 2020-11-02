package parser

import computation.polishnotation.extensions.compute
import models.exception.parserexception.sign.EqualSignAmountException
import models.exception.parserexception.sign.EqualSignPositionException
import models.exception.parserexception.sign.QuestionMarkPositionException
import models.tempVariables
import models.variables
import parser.extensions.putSpaces
import parser.extensions.validateVariable
import parser.getparseable.getParseableDataSet

fun parser(input: String): String {
	val mod = putSpaces(input).split(' ').filter { it.isNotEmpty() }
	val isComputation = mod.contains("?")

	if (!mod.contains("=") && !isComputation) return mod.compute().toString()

	if (mod.count { it == "=" } > 1) throw EqualSignAmountException()

	val indexOfEqual = mod.indexOf("=")
	if (indexOfEqual !in 1 until mod.lastIndex) throw EqualSignPositionException()

	val beforeEqual = mod.subList(0, indexOfEqual)
	val afterEqual = mod.subList(indexOfEqual + 1, mod.size)

	if (isComputation) {
		if (mod.last() != "?") throw QuestionMarkPositionException()
		if (indexOfEqual == mod.lastIndex - 1) return mod.subList(0, indexOfEqual).compute().toString()
		TODO("Computor v1")
	}

	val parseableKClass = getParseableDataSet(mod)
	val variableName = validateVariable(beforeEqual, parseableKClass)

	return afterEqual.compute().also {
		tempVariables.clear()
		variables[variableName] = it
	}.toString()
}