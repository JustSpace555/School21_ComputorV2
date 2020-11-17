package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import computorv1.reducedString
import computorv1.simplify
import globalextensions.getBracketList
import globalextensions.mapToPolynomialList
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class Brackets(override val listOfOperands: List<DataSet> = listOf()): Wrapping() {

	constructor(vararg elements: DataSet) : this(elements.toList())

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Brackets::class, Matrix::class, '+')
			is Brackets -> addElements(other.listOfOperands).tryCastToPolynomialTerm()
			else -> if (other is Numeric && other.isZero())
				this
			else
				addElements(other)
		}

	override fun minus(other: DataSet): DataSet {
		val minusOne = SetNumber(-1)

		return when(other) {
			is Matrix -> throw IllegalOperationException(Brackets::class, Matrix::class, '-')
			is Brackets -> addElements(other.listOfOperands.map { it * minusOne }).tryCastToPolynomialTerm()
			else -> if (other is Numeric && other.isZero())
				this
			else
				addElements(other * minusOne)
		}
	}

	override fun times(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '*')

			is Brackets -> {
				val newFunctionList = mutableListOf<Brackets>()
				other.listOfOperands.forEach {
					newFunctionList.add(applyOnEachElement(DataSet::times, it))
				}

				Brackets(newFunctionList.flatMap { it.getBracketList() }.simplifyPolynomials()).tryCastToPolynomialTerm()
			}

			else -> when {
				other is Numeric && other.isZero() -> other
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> applyOnEachElement(DataSet::times, other).tryCastToPolynomialTerm()
			}
		}

	override fun div(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, '/')

			is Brackets, is Function -> Fraction(this, other)

			is PolynomialTerm -> {
				if (listOfOperands.all { it is PolynomialTerm || it is Numeric }) {
					applyOnEachElement(DataSet::div, other).tryCastToPolynomialTerm()
				} else {
					Fraction(this, other)
				}
			}

			else -> when {
				other is Numeric && other.isZero() -> throw DivideByZeroException()
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> applyOnEachElement(DataSet::div, other).tryCastToPolynomialTerm()
			}
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int || other < 0)
			throw IllegalOperationException(this::class, other::class, '^')

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when(number) {
			0 -> SetNumber(1)
			1 -> this
			else -> {
				var newBrackets = this
				repeat(number - 1) { newBrackets = (newBrackets * newBrackets) as Brackets }

				return if (belowZero)
					Fraction(SetNumber(1), newBrackets.tryCastToPolynomialTerm())
				else
					newBrackets.tryCastToPolynomialTerm()
			}
		}
	}

	override fun toString(): String =
		if (listOfOperands.size == 1)
			listOfOperands.first().toString()
		else
			"(${listOfOperands.mapToPolynomialList().reducedString()})"

	override fun equals(other: Any?): Boolean =
		when {
			other == null -> false
			other !is Brackets -> false
			this === other -> true
			listOfOperands.size != other.listOfOperands.size -> false
			else -> {
				val pairList = listOfOperands.mapToPolynomialList().zip(other.listOfOperands.mapToPolynomialList())
				pairList.all { (el1, el2) -> el1 == el2 }
			}
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31  * result + listOfOperands.hashCode()
		return result
	}

	private fun addElements(elements: List<DataSet>) =
		Brackets((listOfOperands + elements).simplifyPolynomials())

	private fun addElements(vararg elements: DataSet) =
		Brackets((listOfOperands + elements.toList()).simplifyPolynomials())

	private fun applyOnEachElement(
		function: (DataSet, DataSet) -> DataSet,
		element: DataSet
	): Brackets {

		if (element is SetNumber && element.compareTo(1.0) == 0) return this

		val newElementsList = mutableListOf<PolynomialTerm>().apply {
			listOfOperands.forEach {
				when (val newElement = function(it, element)) {
					is Brackets -> this.addAll(newElement.listOfOperands.mapToPolynomialList())
					is PolynomialTerm -> this.add(newElement)
					else -> this.add(PolynomialTerm(newElement))
				}
			}
		}.simplifyPolynomials()

		return Brackets(newElementsList)
	}

	private fun tryCastToPolynomialTerm(): DataSet =
		when {
			listOfOperands.isEmpty() -> SetNumber(0)
			listOfOperands.size == 1 -> listOfOperands.first()
			else -> this
		}

	private fun List<PolynomialTerm>.simplifyOnlyIterable(): List<DataSet> {
		val filtered = this
			.mapToPolynomialList()
			.filter { it.number is Numeric }
			.also { if (it.isEmpty()) return this }
			.simplify()

		return mutableListOf<PolynomialTerm>().apply {
			filtered.forEach {
				this.addAll(if (it.number is Brackets) it.number.listOfOperands.mapToPolynomialList() else listOf(it))
			}
			addAll(this@simplifyOnlyIterable.filter { it.number !is Numeric })
			sortByDescending { it.degree }
		}.flatMap { if (it.degree == 0) it.number.getBracketList() else listOf(it) }
	}

	private fun List<DataSet>.simplifyPolynomials(): List<DataSet> = mapToPolynomialList().simplifyOnlyIterable()
}