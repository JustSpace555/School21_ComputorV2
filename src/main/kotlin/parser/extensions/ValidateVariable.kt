package parser.extensions

import models.dataset.Function
import models.exceptions.computorv2.parserexception.variable.InvalidVariableFormatException
import models.exceptions.computorv2.parserexception.variable.InvalidVariableNameException
import models.reservedFunctionNames
import kotlin.reflect.KClass

fun validateVariable(beforeEqual: List<String>, inputKClass: KClass<*>): String {
	val rightSize = if (inputKClass == Function::class) 4 else 1

	if (beforeEqual.size != rightSize) throw InvalidVariableFormatException()

	val name = beforeEqual.first()
	if (name == "i" || name.contains(Regex("[0-9]")) || name in reservedFunctionNames)
		throw InvalidVariableNameException(name)

	return name
}