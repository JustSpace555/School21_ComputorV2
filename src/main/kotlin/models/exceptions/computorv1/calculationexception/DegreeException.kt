package models.exceptions.computorv1.calculationexception

import models.exceptions.computorv1.ComputorV1Exception
import models.exceptions.globalexceptions.CalculationException

@ComputorV1Exception
class TooHighPolynomialDegreeException : CalculationException() {
	override val message: String = "The polynomial degree is strictly greater than 2, I can't solve it."
}