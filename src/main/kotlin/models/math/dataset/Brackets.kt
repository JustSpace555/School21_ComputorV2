package models.math.dataset

import computorv1.models.PolynomialTerm
import computorv1.reducedString
import computorv1.simplify
import globalextensions.minus
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber


class Brackets(val listOfOperands: List<PolynomialTerm> = listOf()): DataSet {

	constructor(vararg elements: PolynomialTerm) : this(elements.toList())

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')

			is Numeric, is Function -> addElements(elements = arrayOf(PolynomialTerm(other))).tryCastToPolynomialTerm()

			is PolynomialTerm -> addElements(elements = arrayOf(other)).tryCastToPolynomialTerm()

			else -> {
				var newFunctionBrackets = this
				(other as Brackets).listOfOperands.forEach {
					newFunctionBrackets = addElements(newFunctionBrackets, it)
				}

				newFunctionBrackets.tryCastToPolynomialTerm()
			}
		}

	override fun minus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')

			is Numeric, is Function -> addElements(elements = arrayOf(PolynomialTerm((other * SetNumber(-1)) as Numeric)))
				.tryCastToPolynomialTerm()

			is PolynomialTerm -> addElements(elements = arrayOf((other * SetNumber(-1)) as PolynomialTerm))
				.tryCastToPolynomialTerm()

			else -> {
				var newFunctionBrackets = this
				(other as Brackets).listOfOperands.forEach {
					newFunctionBrackets =
						addElements(newFunctionBrackets, (it * SetNumber(-1)) as PolynomialTerm)
				}

				newFunctionBrackets.tryCastToPolynomialTerm()
			}
		}

	override fun times(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '*')

			is Numeric, is Function -> applyOnEachElement(function = PolynomialTerm::times, element = PolynomialTerm(other))

			is PolynomialTerm -> applyOnEachElement(function = PolynomialTerm::times, element = other)

			else -> {
				val newFunctionList = mutableListOf<Brackets>()
				(other as Brackets).listOfOperands.forEach {
					newFunctionList.add(applyOnEachElement(PolynomialTerm::times, it))
				}

				var newFunctionBrackets = Brackets()
				newFunctionList.forEach {
					newFunctionBrackets = (newFunctionBrackets + it) as Brackets
				}

				newFunctionBrackets.tryCastToPolynomialTerm()
			}
		}

	override fun div(other: DataSet): DataSet =
		when(other) {
			is Matrix, is Brackets -> throw IllegalOperationException(this::class, other::class, '/')

			is Numeric, is Function -> applyOnEachElement(function = PolynomialTerm::div, element = PolynomialTerm(other))
				.tryCastToPolynomialTerm()

			else -> applyOnEachElement(function = PolynomialTerm::div, element = other as PolynomialTerm)
				.tryCastToPolynomialTerm()
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number is Double)
			throw IllegalOperationException(this::class, other::class, '^')

		var newFunction = this as DataSet
		repeat((other.number - 1) as Int) { newFunction *= newFunction }
		return newFunction
	}

	override fun toString(): String = listOfOperands.reducedString()

	override fun equals(other: Any?): Boolean =
		when {
			other == null -> false
			other !is Brackets -> false
			this === other -> true
			listOfOperands.size != other.listOfOperands.size -> false
			else -> {
				val pairList = listOfOperands.zip(other.listOfOperands)
				pairList.all { (el1, el2) -> el1 == el2}
			}
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31  * result + listOfOperands.hashCode()
		return result
	}

	private fun addElements(brackets: Brackets = this, vararg elements: PolynomialTerm) =
		Brackets((brackets.listOfOperands + elements.toList()).simplify())

	private fun applyOnEachElement(
		function: (PolynomialTerm, PolynomialTerm) -> DataSet,
		element: PolynomialTerm
	): Brackets {
		val newElementsList = mutableListOf<PolynomialTerm>()
		listOfOperands.forEach {
			when (val newElement = function(it, element)) {
				is Brackets -> newElementsList.addAll(newElement.listOfOperands)
				else -> newElementsList.add(newElement as PolynomialTerm)
			}
		}

		return Brackets(newElementsList.simplify())
	}

	private fun tryCastToPolynomialTerm(): DataSet =
		when {
			listOfOperands.isEmpty() -> SetNumber(0)
			listOfOperands.size == 1 -> listOfOperands.first().tryCastToNumeric()
			else -> this
		}
}