package models.exceptions.computorv2.calcexception

import models.exceptions.computorv2.ComputorV2Exception
import models.exceptions.globalexceptions.CalculationException
import kotlin.reflect.KFunction

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
	override val message: String = "Degree $degree can't be calculated by $function"
}