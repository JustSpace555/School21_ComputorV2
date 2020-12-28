package models.exceptions.computorv1.parserexception

import models.exceptions.computorv1.ComputorV1Exception
import models.exceptions.globalexceptions.parser.ParserException

@ComputorV1Exception
class WrongNumberFormatException(numberString: String? = null) : ParserException() {
	override val message: String = "Wrong number format $numberString"
}

@ComputorV1Exception
class WrongDegreeFormatException(degreeString: String? = null) : ParserException() {
	override val message: String = "Wrong degree format $degreeString"
}

@ComputorV1Exception
class WrongArgumentNameException(argument: String) : ParserException() {
	override val message: String = "Wrong argument name $argument"
}