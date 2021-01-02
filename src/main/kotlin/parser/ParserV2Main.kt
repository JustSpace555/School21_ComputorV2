package parser

import computation.polishnotation.extensions.compute
import computorv1.computorv1
import computorv1.reducedString
import globalextensions.getBracketList
import globalextensions.mapToPolynomialList
import globalextensions.toPolynomialList
import models.dataset.Function
import models.dataset.wrapping.Wrapping
import models.exceptions.computorv2.parserexception.sign.QuestionMarkPositionException
import models.exceptions.computorv2.parserexception.variable.MultipleArgumentException
import models.operationsStringList
import models.variables
import computorv1.parser.extensions.putSpacesComputorV1
import graph.Graph
import models.exceptions.computorv1.EqualSignAmountException
import models.exceptions.computorv1.EqualSignPositionException
import parser.extensions.deleteSpacesFromMultipleOperations
import parser.extensions.getStringWithFunctions
import parser.extensions.putSpaces
import parser.extensions.validateVariable
import parser.getparseable.getParseableDataSet

fun parser(input: String, isPlot: Boolean = false): String {
	var mod = putSpaces(input)
		.split(' ')
		.deleteSpacesFromMultipleOperations()
		.filter { it.isNotEmpty() }

	if (isPlot) mod = mod.drop(1)

	val isComputation = mod.contains("?")

	if (!mod.contains("=") && !isComputation) return mod.compute().toString()
//		val res = mod.compute()
//		Graph().draw(res)
//		return res.toString()
//	}

	if (mod.count { it == "=" } > 1) throw EqualSignAmountException()

	val indexOfEqual = mod.indexOf("=")
	if (indexOfEqual !in 1 until mod.lastIndex) throw EqualSignPositionException()

	val beforeEqual = mod.subList(0, indexOfEqual)
	val afterEqual = mod.subList(indexOfEqual + 1, mod.size)

	if (isComputation) {
		if (mod.last() != "?") throw QuestionMarkPositionException()
		if (indexOfEqual == mod.lastIndex - 1) return beforeEqual.compute().toString()

		val findParameter: List<String>.() -> String = {
			find {
				!(variables.containsKey(it) || it.contains(Regex("[0-9]")) || it in operationsStringList)
			} ?: ""
		}

		val parameterBeforeEqual = beforeEqual.findParameter()
		val parameterAfterEquals = afterEqual.findParameter()

		if (parameterBeforeEqual.isNotEmpty() && parameterAfterEquals.isNotEmpty() &&
			parameterBeforeEqual != parameterAfterEquals
		) {
			throw MultipleArgumentException()
		}

		val rightParameter = if (parameterBeforeEqual.isNotEmpty()) parameterBeforeEqual else parameterAfterEquals

		val prepareStringForComputorV1: (List<String>) -> String = {
			getStringWithFunctions(it)
				.compute(rightParameter)
				.getBracketList()
				.mapToPolynomialList()
				.reducedString(rightParameter)
				.replace(rightParameter, "X")
		}

		val computedBeforeEqual = prepareStringForComputorV1(beforeEqual)
		val computedAfterEqual = prepareStringForComputorV1(afterEqual.dropLast(1))

		return computorv1("$computedBeforeEqual = $computedAfterEqual").replace("X", rightParameter)
	}

	val parseableKClass = getParseableDataSet(mod)
	val variableName = validateVariable(beforeEqual, parseableKClass)

	val isFunction = parseableKClass == Function::class
	val parameter = if (isFunction) beforeEqual[2] else ""
	val computed = afterEqual.compute(parameter).also {
		variables[variableName] = if (isFunction) { Function(parameter, it.toPolynomialList()) } else it
	}

	return when(computed) {
		is Wrapping -> computed.toString().removePrefix("(").removeSuffix(")")
		else -> computed.toString()
	}
}