package models.exception.calcexception.variable

import models.exception.calcexception.CalculationException
import kotlin.reflect.KClass

abstract class VariableCalculationException : CalculationException()

class NoSuchVariableException(invalidVariableName: String = ""): VariableCalculationException() {
	override val message: String = "There is no such variable in memory" +
			if (invalidVariableName.isEmpty()) "" else ": $invalidVariableName"
}

class IllegalOperationException(arg1: KClass<*>, arg2: KClass<*>, `fun`: Char) : CalculationException() {
	override val message: String = "Illegal operation " +
			"\"$`fun`\" between ${arg1.simpleName} and ${arg2.simpleName}"
}