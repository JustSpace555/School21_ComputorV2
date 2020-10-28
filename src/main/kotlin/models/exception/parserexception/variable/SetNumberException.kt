package models.exception.parserexception.variable

import kotlin.reflect.KClass

abstract class SetNumberException : VariableParserException()

class SetNumericFormatException(input: String = "", parseTo: KClass<*>? = null) : SetNumberException() {
    override val message: String = "SetNumber format exception. " +
            if (input.isNotEmpty() && parseTo != null) "Can't parse $input to ${parseTo.simpleName}" else ""
}