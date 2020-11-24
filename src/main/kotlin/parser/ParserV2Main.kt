package parser

import computation.polishnotation.extensions.compute
import computorv1.computorV1
import computorv1.reducedString
import globalextensions.getBracketList
import globalextensions.mapToPolynomialList
import globalextensions.toPolynomialList
import models.dataset.function.Function
import models.dataset.wrapping.Brackets
import models.exceptions.computorv1.parserexception.EqualSignAmountException
import models.exceptions.computorv1.parserexception.EqualSignPositionException
import models.exceptions.computorv2.parserexception.sign.QuestionMarkPositionException
import models.exceptions.computorv2.parserexception.variable.MultipleArgumentException
import models.variables
import parser.extensions.putSpaces
import parser.extensions.validateVariable
import parser.getparseable.getParseableDataSet

internal fun parser(input: String): String {
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
		if (indexOfEqual == mod.lastIndex - 1) return beforeEqual.compute().toString()

		val parameter = beforeEqual.find {
			!variables.containsKey(it) && !it.contains(Regex("[0-9+\\-*/^()%;\\[\\],]"))
		} ?: ""

		val parameterAfterEquals = afterEqual.dropLast(1).find {
			!variables.containsKey(it) && !it.contains(Regex("[0-9+\\-*/^()%;\\[\\],]"))
		} ?: ""

		if (parameter.isNotEmpty() && parameterAfterEquals.isNotEmpty() && parameter != parameterAfterEquals)
			throw MultipleArgumentException()

		val computedBeforeEqual = getStringWithFunctions(beforeEqual)
			.compute(parameter)
			.getBracketList()
			.mapToPolynomialList()
			.reducedString(parameter)
			.replace(parameter, "X")

		val computedAfterEqual = getStringWithFunctions(afterEqual.dropLast(1))
			.compute(parameter)
			.getBracketList()
			.mapToPolynomialList()
			.reducedString(parameter)
			.replace(parameter, "X")

		return computorV1("$computedBeforeEqual = $computedAfterEqual").replace("X", parameter)
	}

	val parseableKClass = getParseableDataSet(mod)
	val variableName = validateVariable(beforeEqual, parseableKClass)

	val isFunction = parseableKClass == Function::class
	val parameter = if (isFunction) beforeEqual[2] else ""
	val computed = afterEqual.compute(parameter).also {
		variables[variableName] = if (isFunction) { Function(parameter, it.toPolynomialList()) } else it
	}

	return when(computed) {
		is Brackets -> computed.toString().removePrefix("(").removeSuffix(")")
		else -> computed.toString()
	}
}

//TODO Подумать как переписать
fun getStringWithFunctions(input: List<String>): List<String> {
	var i = 0
	val output = mutableListOf<String>()

	while (i in input.indices) {
		if (variables.containsKey(input[i]) && variables[input[i]] is Function) {
			val function = variables[input[i]] as Function
			output.addAll(putSpaces(function.function.reducedString(function.parameter)).split(' '))
			i += 4
			continue
		}
		output.add(input[i++])
	}
	return output
}