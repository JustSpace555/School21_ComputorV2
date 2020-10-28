package parser.variable

import models.math.dataset.Function
import models.math.dataset.numeric.Numeric
import models.variables
import parser.variable.numeric.toNumeric

fun parseAndInvokeFunctionFromList(input: List<String>): Numeric {
	val function = variables[input.first()] as Function
	val number = input.last { it != "(" && it != ")" }.toNumeric()
	return function(number)
}