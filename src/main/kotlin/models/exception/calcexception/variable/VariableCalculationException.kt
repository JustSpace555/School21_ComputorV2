package models.exception.calcexception.variable

import models.exception.calcexception.CalculationException
import kotlin.reflect.KClass

abstract class VariableCalculationException : CalculationException()

class NoSuchVariableException(invalidVariableName: String = ""): VariableCalculationException() {
	override val message: String = "There is no such variable in memory" +
			if (invalidVariableName.isEmpty()) "" else ": $invalidVariableName"
}

class UnavailableOperation(type1: KClass<*>, type2: KClass<*>, `fun`: Char) : VariableCalculationException() {
	override val message: String =
			"Unavailable operation \"$`fun`\" between ${type1.simpleName} and ${type2.simpleName}"
}