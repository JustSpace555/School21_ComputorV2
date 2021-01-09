package models.exceptions.computorv2.calcexception

import computation.SAMPLE_FACTORIAL_BORDER
import models.exceptions.computorv2.ComputorV2Exception
import models.exceptions.globalexceptions.CalculationException

@ComputorV2Exception
class IllegalTokenException(token: String = "") : CalculationException() {
	override val message = "Illegal token" + if (token.isEmpty()) "" else ": $token"
}

@ComputorV2Exception
class TooFewOperatorsException: CalculationException() {
	override val message: String = "Too few operators"
}

@ComputorV2Exception
class BracketsAmountException : CalculationException() {
	override val message: String = "Wrong amount of brackets"
}

@ComputorV2Exception
class DivideByZeroException : CalculationException() {
	override val message: String = "Division by zero"
}

@ComputorV2Exception
class NonExistDegree(degree: Double, function: String) : CalculationException() {
	override val message: String = "Degree $degree can't be calculated by $function because it's value is NaN"
}

@ComputorV2Exception
class FactorialInfinityException(number: Int) : CalculationException() {
	override val message: String =
		"Factorial of $number is infinity. Max value for factorial is $SAMPLE_FACTORIAL_BORDER."
}