package models.exception.calcexception

import models.exception.ComputorException

abstract class CalculationException: ComputorException()

class NumberFormatException: CalculationException() {
	override val message: String = "Wrong number format"
}

class IllegalTokenException(token: String = "") : CalculationException() {
	override val message = "Illegal token" +
			if (token.isNotEmpty()) ": $token"
			else ""
}

//class TooFewOperandsException: CalculationException() {
//	override val message: String = "Too few operands"
//}

class TooFewOperatorsException: CalculationException() {
	override val message: String = "Too few operators"
}

class BracketsAmountException : CalculationException() {
	override val message: String = "Wrong amount of brackets"
}

class DivideByZeroException : CalculationException() {
	override val message: String = "Division by zero"
}