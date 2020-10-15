package models.exception.parserexception.variable

import models.exception.parserexception.ParserException

abstract class VariableException: ParserException()

class NoSuchVariableException(invalidVariableName: String = ""): VariableException() {
	override val message: String = "There is no such variable in memory" +
			if (invalidVariableName.isEmpty()) "" else ": $invalidVariableName"
}

class InvalidVariableFormatException: VariableException() {
	override val message: String = "Invalid variable format"
}

class InvalidVariableNameException(invalidName: String = ""): VariableException() {
	override val message: String = "Invalid variable name" + if (invalidName.isEmpty()) "" else ": $invalidName"
}