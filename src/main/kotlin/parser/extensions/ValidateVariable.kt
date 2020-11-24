package parser.extensions

import models.dataset.function.Function
import models.exceptions.computorv2.parserexception.variable.InvalidVariableFormatException
import models.exceptions.computorv2.parserexception.variable.InvalidVariableNameException
import kotlin.reflect.KClass

fun validateVariable(beforeEqual: List<String>, inputKClass: KClass<*>): String {
	val rightSize = when(inputKClass) {
		Function::class -> 4
		else -> 1
	}

	if (beforeEqual.size != rightSize) throw InvalidVariableFormatException()

	val listOfFunctions = listOf("sin", "cos", "tan", "cot", "asin", "acos", "atan", "acot", "exp", "sqrt", "abs")

	val name = beforeEqual.first()
	if (name == "i" || name.contains(Regex("[0-9]")) || name in listOfFunctions)
		throw InvalidVariableNameException(name)

	return name
}