package parser.variable

import computation.polishnotation.extensions.compute
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.numeric.Numeric
import models.tempVariables
import models.variables
import parser.variable.numeric.toNumeric

private fun Map<String, DataSet>.checkIsElementNumeric(name: String) {
	val element = this.getOrElse(name) { return }
	if (element !is Numeric) throw IllegalOperationException(Function::class, element::class)
}

fun parseFunctionFromList(beforeEqual: List<String>, afterEqual: List<String>) = Function(beforeEqual[2], afterEqual)

fun parseAndInvokeFunctionFromList(input: List<String>): Numeric {
	val function = variables[input.first()] as Function
	val numberList = input.subList(input.indexOf("(") + 1, input.lastIndexOf(")"))
	val number = if (numberList.size == 1) {
		val varName = numberList.first()
		when {
			variables.containsKey(varName) -> {
				variables.checkIsElementNumeric(varName)
				variables[varName]
			}
			tempVariables.containsKey(varName) -> {
				tempVariables.checkIsElementNumeric(varName)
				tempVariables[varName]
			}
			else -> varName.toNumeric()
		}
	} else {
		numberList.compute()
	} as Numeric

	return function(number)
}