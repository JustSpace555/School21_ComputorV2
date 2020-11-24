package models

import models.dataset.DataSet

val variables = mutableMapOf<String, DataSet>()

val tempVariables = mutableMapOf<String, DataSet>()

val history = mutableListOf<Pair<String, String>>()

fun putTempVariable(inputVar: DataSet): String {
	val name = "var_${tempVariables.size + 1}"
	tempVariables[name] = inputVar
	return name
}