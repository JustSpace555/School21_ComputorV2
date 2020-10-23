package parser.variable

import models.math.dataset.Function
import models.math.dataset.numeric.Numeric
import models.math.variables
import parser.variable.numeric.toNumeric

fun List<String>.parseAndInvokeFunctionFromListString(): Numeric {
	val function = variables[first()] as Function
	val number = this.last { it != "(" && it != ")" }.toNumeric()
	return function(number)
}