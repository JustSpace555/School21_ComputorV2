package models.exceptions.computorv1

import models.exceptions.ComputorException

@ComputorV1Exception
abstract class ArgumentException : ComputorException()

@ComputorV1Exception
class PolynomialArgumentNameException(value: Any) : ArgumentException() {
	override val message: String = "Wrong argument '$value' name. Must be 'x' or 'X'."
}