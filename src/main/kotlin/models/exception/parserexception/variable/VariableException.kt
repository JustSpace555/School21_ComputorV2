package models.exception.parserexception.variable

import models.exception.parserexception.ParserException

abstract class VariableException: ParserException()

class NoSuchVariableException: VariableException() {
	override val message: String = "There is no such variable in memory"
}
class InvalidVariableNameException: VariableException() {
	override val message: String = "Invalid variable name"
}