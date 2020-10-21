package models.exception.parserexception.variable

import models.exception.parserexception.ParserException
import kotlin.reflect.KClass

abstract class VariableParserException: ParserException()

class InvalidVariableFormatException: VariableParserException() {
	override val message: String = "Invalid variable format"
}

class InvalidVariableNameException(invalidName: String = ""): VariableParserException() {
	override val message: String = "Invalid variable name" + if (invalidName.isEmpty()) "" else ": $invalidName"
}