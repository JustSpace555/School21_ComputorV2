package models.exceptions.computorv1

import models.exceptions.ComputorException

@ComputorV1Exception
abstract class NumberException : ComputorException()

@ComputorV1Exception
class PolynomialNumberFormatException(value: Any) : NumberException() {
	override val message: String = "Wrong number format. Can't parse $value to number"
}

@ComputorV1Exception
class PolynomialDegreeFormatException(value: Any) : NumberException() {
	override val message: String = "Wrong degree format. Can't parse $value to degree (int)"
}