package models.exception.calcexception

import models.exception.ComputorException

abstract class CalculationException: ComputorException()

class IllegalTokenException(token: String = "") : CalculationException() {
	override val message = "Illegal token" + if (token.isEmpty()) "" else ": $token"
}

class TooFewOperatorsException: CalculationException() {
	override val message: String = "Too few operators"
}

class BracketsAmountException : CalculationException() {
	override val message: String = "Wrong amount of brackets"
}

class DivideByZeroException : CalculationException() {
	override val message: String = "Division by zero"
}