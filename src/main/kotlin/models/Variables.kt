package models

import models.math.dataset.DataSet
import kotlin.reflect.KClass

val variables = mutableMapOf<String, DataSet>()

//TODO Переделать чтобы клалось просто название + переменная, а не пара лист + класс
val tempVariables = mutableMapOf<String, Pair<List<String>, KClass<*>>>()

fun putTempVariable(inputList: List<String>, inputKClass: KClass<*>): String {
	val name = "var_${tempVariables.size + 1}"

	tempVariables[name] = Pair(inputList, inputKClass)

	return name
}