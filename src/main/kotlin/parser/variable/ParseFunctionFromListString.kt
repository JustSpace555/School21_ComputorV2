package parser.variable

import computation.polishnotation.extensions.compute
import computorv1.models.PolynomialTerm
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.Brackets
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Numeric
import models.tempVariables
import models.variables
import parser.variable.numeric.toNumeric

private fun Map<String, DataSet>.checkIsElementNumeric(name: String) {
	val element = this.getOrElse(name) { return }
	if (element !is Numeric) throw IllegalOperationException(Function::class, element::class)
}

fun DataSet.toPolynomialList() =
	when(this) {
		is Brackets -> this.listOfOperands
		is PolynomialTerm -> listOf(this)
		else -> listOf(PolynomialTerm(this as Numeric))
	}

fun parseFunctionFromList(beforeEqual: List<String>, afterEqual: List<String>): Function =
	Function(beforeEqual[2], afterEqual.compute(beforeEqual[2]).toPolynomialList())

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
		numberList.compute()
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