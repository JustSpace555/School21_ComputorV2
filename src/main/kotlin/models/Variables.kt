package models

import models.dataset.DataSet

val variables = mutableMapOf<String, DataSet>()

val tempVariables = mutableMapOf<String, DataSet>()

fun putTempVariable(inputVar: DataSet): String {
	val name = "var_${tempVariables.size + 1}"
	tempVariables[name] = inputVar
	return name
}