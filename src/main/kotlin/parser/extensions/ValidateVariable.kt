package parser.extensions

import models.exception.parserexception.variable.InvalidVariableFormatException
import models.exception.parserexception.variable.InvalidVariableNameException
import models.math.dataset.Function
import kotlin.reflect.KClass

private fun List<String>.checkSize() { if (this.size != 1) throw InvalidVariableFormatException() }

fun validateVariable(beforeEqual: List<String>, inputKClass: KClass<*>) {
	if (inputKClass != Function::class)
		beforeEqual.checkSize()

	val name = beforeEqual.first()
	if (name == "i" || name.contains(Regex("[0-9]")))
		throw InvalidVariableNameException(name)
}