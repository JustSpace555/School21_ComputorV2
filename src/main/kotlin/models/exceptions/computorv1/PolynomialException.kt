package models.exceptions.computorv1

import models.exceptions.ComputorException

@ComputorV1Exception
abstract class PolynomialException : ComputorException()

@ComputorV1Exception
class PolynomialMaxDegreeException : PolynomialException() {
	override val message: String = "The polynomial degree is strictly greater than 2, I can't solve it."
}