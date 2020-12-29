package models

import computation.SAMPLE_E
import computation.SAMPLE_PI
import models.dataset.DataSet
import models.dataset.numeric.SetNumber

val variables = mutableMapOf<String, DataSet>().apply {
	put("pi", SetNumber(SAMPLE_PI))
	put("e", SetNumber(SAMPLE_E))
}
val tempVariables = mutableMapOf<String, DataSet>()
val history = mutableListOf<Pair<String, String>>()

val reservedFunctionNames = listOf("sin", "cos", "tan", "ctg", "asin", "actg", "atan", "acot", "exp", "sqrt", "abs")
val reservedConstNames = listOf("pi", "e")

val basicOperationsCharList = listOf('+', '-', '*', '/')
val basicOperationsStringList = basicOperationsCharList.map { it.toString() }

val operationsCharList = basicOperationsCharList +  listOf('%', '^', '(', ')', '=', '?', ';', '[', ']', ',')
val operationsStringList = operationsCharList.map { it.toString() }
val matrixOperationsList = listOf("++", "--", "**", "//")

fun putTempVariable(inputVar: DataSet): String {
	val name = "var_${tempVariables.size + 1}"
	tempVariables[name] = inputVar
	return name
}