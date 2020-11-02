package models.exceptions.computorv2.calcexception.variable

import models.exceptions.computorv2.ComputorV2Exception
import models.exceptions.globalexceptions.CalculationException
import kotlin.reflect.KClass

@ComputorV2Exception
abstract class VariableCalculationException : CalculationException()

@ComputorV2Exception
class NoSuchVariableException(invalidVariableName: String = ""): VariableCalculationException() {
	override val message: String = "There is no such variable in memory" +
			if (invalidVariableName.isEmpty()) "" else ": $invalidVariableName"
}

@ComputorV2Exception
class IllegalOperationException(
	arg1: KClass<*>, arg2: KClass<*>, `fun`: Char? = null
) : VariableCalculationException() {
	override val message: String = "Illegal operation ${`fun` ?: ""}" +
			"between ${arg1.simpleName} and ${arg2.simpleName}"
}