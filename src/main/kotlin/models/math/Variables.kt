package models.math

import models.math.dataset.DataSet
import kotlin.reflect.KClass

val variables = mutableMapOf<String, DataSet>()

val tempVariables = mutableMapOf<String, Pair<List<String>, KClass<*>>>()