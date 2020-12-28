package models.exceptions.computorv2.parserexception.sign

import models.exceptions.computorv2.ComputorV2Exception
import models.exceptions.globalexceptions.parser.SignException

@ComputorV2Exception
abstract class QuestionMarkException : SignException()

@ComputorV2Exception
class QuestionMarkPositionException : QuestionMarkException() {
	override val message: String = "Question mark must be at the end of exception"
}