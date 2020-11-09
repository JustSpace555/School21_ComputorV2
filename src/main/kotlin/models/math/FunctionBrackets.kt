package models.math

import computorv1.models.PolynomialTerm
import computorv1.reducedString
import computorv1.simplify
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Matrix
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber


class FunctionBrackets(var listOfOperands: MutableList<PolynomialTerm> = mutableListOf()): DataSet {

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')

			is Numeric -> this.apply { addElements(PolynomialTerm(other, 0)) }

			is PolynomialTerm -> this.apply { addElements(other) }

			else -> this.apply {
					(other as FunctionBrackets).listOfOperands.forEach { applyOnEachElement(PolynomialTerm::plus, it) }
			}
		}

	override fun minus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')

			is Numeric -> this.apply { addElements(PolynomialTerm((other * SetNumber(-1)) as Numeric)) }

			is PolynomialTerm -> this.apply { addElements((other * SetNumber(-1)) as PolynomialTerm) }

			else -> this.apply {
				(other as FunctionBrackets).listOfOperands.forEach { applyOnEachElement(PolynomialTerm::minus, it) }
			}
		}

	override fun times(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '*')

			is Numeric -> this.apply {
				applyOnEachElement(PolynomialTerm::times, PolynomialTerm(other))
			}

			is PolynomialTerm -> this.apply { applyOnEachElement(PolynomialTerm::times, other) }

			else -> this.apply {
				(other as FunctionBrackets).listOfOperands.forEach { applyOnEachElement(PolynomialTerm::times, it) }
			}
		}

	override fun div(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')

			is Numeric -> this.apply {
				applyOnEachElement(PolynomialTerm::div, PolynomialTerm(other))
			}

			is PolynomialTerm -> this.apply { applyOnEachElement(PolynomialTerm::div, other) }

			else -> this.apply {
				(other as FunctionBrackets).listOfOperands.forEach { applyOnEachElement(PolynomialTerm::div, it) }
			}
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	override fun toString(): String = listOfOperands.reducedString()

	fun addElements(vararg elements: PolynomialTerm) {
		listOfOperands = listOfOperands.apply { addAll(elements.toList()) }.simplify().toMutableList()
	}

	private fun applyOnEachElement(
		function: (PolynomialTerm, PolynomialTerm) -> DataSet,
		element: PolynomialTerm
	) {
		val newElementsList = mutableListOf<PolynomialTerm>()
			listOfOperands.forEach {
			when (val newElement = function(it, element)) {
				is FunctionBrackets -> newElementsList.addAll(newElement.listOfOperands)
				else -> newElementsList.add(newElement as PolynomialTerm)
			}
		}

		listOfOperands = newElementsList.simplify().toMutableList()
	}

	override fun equals(other: Any?): Boolean =
		when {
			other == null -> false
			this === other -> true
			other !is FunctionBrackets -> false
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
}