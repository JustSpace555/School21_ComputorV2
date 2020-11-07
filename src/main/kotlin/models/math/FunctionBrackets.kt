package models.math

import computation.polishnotation.extensions.compute
import computorv1.getReducedListTermForm
import computorv1.getSimplifiedFunction
import computorv1.getSimplifiedFunctionString
import computorv1.models.PolynomialTerm
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.numeric.SetNumber
import parser.variable.numeric.toSetNumber

class FunctionBrackets(private val parameter: String, val listOfExpressions: MutableList<Any> = mutableListOf()) {

	operator fun invoke(): List<String> {
		if (listOfExpressions.all { it is String }) {

			with(listOfExpressions.filterIsInstance<String>()) {

				return if (!this.contains(parameter)) {
					listOf(this.compute().toString())
				} else {
					this.getSimplifiedAndSplit(parameter)
				}
			}
		}

		val listOfSimplifiedString = mutableListOf<String>()
		listOfExpressions.forEach {
			if (it is FunctionBrackets) listOfSimplifiedString.addAll(it())
			else listOfSimplifiedString.add(it.toString())
		}

		val outputList = if (listOfSimplifiedString.contains("^")) {
			if (listOfSimplifiedString.count { it == "^" } > 1 ||
				listOfSimplifiedString.indexOf("^") != listOfSimplifiedString.lastIndex - 1
			) {
				throw IllegalOperationException(this::class, SetNumber::class, '^')
			}

			val degree = listOfSimplifiedString.last().toString().toSetNumber()
			if (degree.number is Double) throw IllegalOperationException(this::class, SetNumber::class, '^')

			var newFunctionBrackets = FunctionBrackets(parameter, listOfSimplifiedString.toMutableList())
			repeat(degree.number as Int) { newFunctionBrackets *= newFunctionBrackets }

			newFunctionBrackets.listOfExpressions

		} else listOfSimplifiedString

		return outputList.filterIsInstance<String>().getSimplifiedAndSplit(parameter)
	}

	override fun toString(): String = listOfExpressions.joinToString(" ")

	private operator fun times(other: FunctionBrackets): FunctionBrackets {
		val simplifiedThis = listOfExpressions.filterIsInstance<String>().getSimplifiedFunction(parameter)
		val simplifiedOther = other.listOfExpressions.filterIsInstance<String>().getSimplifiedFunction(parameter)

		val outputList = mutableListOf<PolynomialTerm>()
		simplifiedThis.forEach { thisTerm ->
			simplifiedOther.forEach { otherTerm ->
				outputList.add(thisTerm * otherTerm)
			}
		}
		return FunctionBrackets(parameter, outputList.getReducedListTermForm().split(' ').toMutableList())
	}
			val outputList = if (listOfExpressions.contains("^")) {
				if (listOfExpressions.count { it == "^" } > 1 ||
					listOfExpressions.indexOf("^") != listOfExpressions.lastIndex - 1
				) {
					throw IllegalOperationException(this::class, SetNumber::class, '^')
				}

				val degree = listOfExpressions.last().toString().toSetNumber()
				if (degree.number is Double) throw IllegalOperationException(this::class, SetNumber::class, '^')

				var newFunctionBrackets = FunctionBrackets(parameter, listOfExpressions)
				repeat(degree.number as Int) { newFunctionBrackets *= newFunctionBrackets }

				newFunctionBrackets.listOfExpressions

			} else listOfExpressions
	private fun List<String>.getSimplifiedAndSplit(parameter: String) =
		getSimplifiedFunctionString(parameter).split(' ')
}