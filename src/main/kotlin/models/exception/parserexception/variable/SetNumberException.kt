package models.exception.parserexception.variable

abstract class SetNumberException : VariableParserException()

class SetNumberFormatException(input: String = "") : SetNumberException() {
    override val message: String = "SetNumber format exception." +
            if (input.isNotEmpty()) "Can't parse $input to SetNumber" else ""
}