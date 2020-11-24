package parser.variable

import computation.polishnotation.extensions.compute
import computorv1.models.PolynomialTerm
import globalextensions.toPolynomialList
import models.dataset.DataSet
import models.dataset.function.Function
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.tempVariables
import models.variables
import parser.variable.numeric.toNumeric

private fun Map<String, DataSet>.checkIsElementNumeric(name: String) {
	val element = this.getOrElse(name) { return }
	if (element !is Numeric) throw IllegalOperationException(Function::class, element::class)
}

fun parseFunctionFromList(input: List<String>, parameter: String): DataSet {
	val function = variables[input.first()] as Function
	val numberList = input.subList(input.indexOf("(") + 1, input.lastIndexOf(")"))
	val number = if (numberList.size == 1) {
		val varName = numberList.first()
		when {
			variables.containsKey(varName) -> {
				variables.checkIsElementNumeric(varName)
				variables[varName]!!
			}
			tempVariables.containsKey(varName) -> {
				tempVariables.checkIsElementNumeric(varName)
				tempVariables[varName]!!
			}
			varName == parameter -> PolynomialTerm(1, 1, parameter)
			else -> varName.toNumeric()
		}
	} else {
		numberList.compute(parameter)
	}

	if (number is Matrix) throw IllegalOperationException(Function::class, Matrix::class)

	return if (number is Numeric) {
		function(number)
	} else {
		Function(
			parameter, function.function.map { it.copy(name = parameter) }, input.first()
		).apply { addPolynomialsBeforeInvoke(number.toPolynomialList()) }
	}
}