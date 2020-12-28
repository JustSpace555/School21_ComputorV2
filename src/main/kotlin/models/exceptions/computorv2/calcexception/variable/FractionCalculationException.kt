package models.exceptions.computorv2.calcexception.variable

import models.exceptions.computorv2.ComputorV2Exception
import models.exceptions.globalexceptions.CalculationException
import models.dataset.wrapping.Fraction

@ComputorV2Exception
abstract class FractionCalculationException : CalculationException()

@ComputorV2Exception
class NotEqualDenominatorException(f1: Fraction, f2: Fraction, operation: Char) : FractionCalculationException() {
	override val message: String = "Unavailable to do operation $operation between fraction $f1 and $f2 " +
			"because their denominators are not equal"
}