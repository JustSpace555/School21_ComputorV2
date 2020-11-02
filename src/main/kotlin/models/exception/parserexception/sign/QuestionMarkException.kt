package models.exception.parserexception.sign

abstract class QuestionMarkException : SignException()

class QuestionMarkPositionException : QuestionMarkException() {
	override val message: String = "Question mark must be at the end of exception"
}