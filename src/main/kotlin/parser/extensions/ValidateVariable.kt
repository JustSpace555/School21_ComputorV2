package parser.extensions

import models.exceptions.computorv2.parserexception.variable.InvalidVariableFormatException
import models.exceptions.computorv2.parserexception.variable.InvalidVariableNameException
import models.math.dataset.Function
import kotlin.reflect.KClass

private fun List<String>.checkSize(rightSize: Int) { if (size != rightSize) throw InvalidVariableFormatException() }

fun validateVariable(beforeEqual: List<String>, inputKClass: KClass<*>): String {
	beforeEqual.checkSize(
		when (inputKClass) {
			Function::class -> 4
			else -> 1
		}
	)

	val name = beforeEqual.first()
	if (name == "i" || name.contains(Regex("[0-9]"))) throw InvalidVariableNameException(name)

	return name
}