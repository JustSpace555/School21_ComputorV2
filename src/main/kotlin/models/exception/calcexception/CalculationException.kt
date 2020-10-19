package models.exception.calcexception

import models.exception.ComputorException
import kotlin.reflect.KClass

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

class IllegalOperationException(arg1: KClass<*>, arg2: KClass<*>, `fun`: Char) : CalculationException() {
	override val message: String = "Illegal operation " +
			"\"$`fun`\" between ${arg1.simpleName} and ${arg2.simpleName}"
}