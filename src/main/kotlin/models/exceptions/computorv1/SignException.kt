package models.exceptions.computorv1

import models.exceptions.ComputorException

@ComputorV1Exception
abstract class SignException : ComputorException()

@ComputorV1Exception
class EqualSignAmountException : SignException() {
	override val message: String = "Wrong amount of equal signs. Must be 1."
}

@ComputorV1Exception
class EqualSignPositionException: SignException() {
	override val message: String = "Wrong equal sign position"
}