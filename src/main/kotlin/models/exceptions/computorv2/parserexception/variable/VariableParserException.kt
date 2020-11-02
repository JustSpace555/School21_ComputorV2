package models.exceptions.computorv2.parserexception.variable

import models.exceptions.computorv2.ComputorV2Exception
import models.exceptions.globalexceptions.parser.ParserException

@ComputorV2Exception
abstract class VariableParserException: ParserException()

@ComputorV2Exception
class InvalidVariableFormatException: VariableParserException() {
	override val message: String = "Invalid variable format"
}

@ComputorV2Exception
class InvalidVariableNameException(invalidName: String = ""): VariableParserException() {
	override val message: String = "Invalid variable name" + if (invalidName.isEmpty()) "" else ": $invalidName"
}