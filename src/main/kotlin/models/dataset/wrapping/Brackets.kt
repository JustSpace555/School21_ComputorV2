package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import computorv1.reducedString
import computorv1.simplify
import globalextensions.getBracketList
import globalextensions.isEmpty
import globalextensions.mapToPolynomialList
import globalextensions.toPolynomial
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class Brackets(override val listOfOperands: List<DataSet> = listOf()): Wrapping() {

	constructor(vararg elements: DataSet) : this(elements.toList())

	override val isEmpty: Boolean = listOfOperands.isEmpty()

	override fun plus(other: DataSet): DataSet {
		if (other is Matrix) throw IllegalOperationException(Brackets::class, Matrix::class, "+")

		if (isEmpty) return other

		return when (other) {
			is Brackets -> addElements(other.listOfOperands)
			else -> if (other.isEmpty()) this else addElements(other)
		}
	}

	override fun minus(other: DataSet): DataSet {
		if (other is Matrix) throw IllegalOperationException(Brackets::class, Matrix::class, "-")

		val minusOne = SetNumber(-1)
		if (isEmpty) return other * minusOne

		return when (other) {
			is Brackets -> addElements(other.listOfOperands.map { it * minusOne })
			else -> if (other.isEmpty()) this else addElements(other * minusOne)
		}
	}

	override fun times(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(Brackets::class, Matrix::class, "*")

			isEmpty || other.isEmpty() -> SetNumber()

			other is SetNumber && other.compareTo(1.0) == 0 -> this

			else -> when (other) {
				is Brackets -> {
					val newFunctionList = mutableListOf<DataSet>()
					other.listOfOperands.forEach {
						newFunctionList.add(applyOnEachElement(DataSet::times, it))
					}

					Brackets(newFunctionList.flatMap { it.getBracketList() }.simplifyPolynomials()).reduceList()
				}

				else -> applyOnEachElement(DataSet::times, other)
			}
	}

	override fun div(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(Brackets::class, Matrix::class, "/")

			other.isEmpty() -> throw DivideByZeroException()

			isEmpty -> SetNumber()

			other is SetNumber && other.compareTo(1.0) == 0 -> this

			else -> when (other) {
				is Wrapping, is Function -> Fraction(this, other)

				is PolynomialTerm -> {
					if (listOfOperands.all { it is PolynomialTerm || it is Numeric }) {
						applyOnEachElement(DataSet::div, other)
					} else {
						Fraction(this, other)
					}
				}

				else -> applyOnEachElement(DataSet::div, other)
			}
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(Brackets::class, other::class, "%")

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(Brackets::class, other::class, "^")

		if (isEmpty) return SetNumber()

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when(number) {
			0 -> SetNumber(1)
			1 -> this
			else -> {
				var newBrackets = this as DataSet
				repeat(number - 1) { newBrackets *= newBrackets }

				val output = if (newBrackets is Brackets) newBrackets.reduceList() else newBrackets
				if (belowZero)
					Fraction(SetNumber(1), output)
				else
					output
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
				val pairList = listOfOperands.zip(other.listOfOperands)
				pairList.all { (el1, el2) -> el1 == el2 }
			}
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31  * result + listOfOperands.hashCode()
		return result
	}

	private fun addElements(elements: List<DataSet>) =
		Brackets((listOfOperands + elements).simplifyPolynomials()).reduceList()

	private fun addElements(vararg elements: DataSet) =
		Brackets((listOfOperands + elements.toList()).simplifyPolynomials()).reduceList()

	private fun applyOnEachElement(
		function: (DataSet, DataSet) -> DataSet,
		element: DataSet
	): DataSet {

		if (element is SetNumber && element.compareTo(1.0) == 0) return this

		val newElementsList = mutableListOf<PolynomialTerm>().apply {
			listOfOperands.forEach {
				when (val newElement = function(it, element)) {
					is Brackets -> addAll(newElement.listOfOperands.mapToPolynomialList())
					else -> add(newElement.toPolynomial())
				}
			}
		}.simplifyPolynomials()

		return Brackets(newElementsList).reduceList()
	}

	private fun List<PolynomialTerm>.simplifyOnlyIterable(): List<DataSet> {
		val getDataList: List<PolynomialTerm>.() -> List<DataSet> = {
			this.flatMap {
				if (it.degree == 0) it.number.getBracketList() else listOf(it)
			}
		}

		val filtered = this
			.filter { it.number is Numeric }
			.also { if (it.isEmpty()) return this.getDataList() }
			.simplify()

		return mutableListOf<PolynomialTerm>().apply {
			filtered.forEach {
				this.addAll(if (it.number is Brackets) it.number.listOfOperands.mapToPolynomialList() else listOf(it))
			}
			addAll(this@simplifyOnlyIterable.filter { it.number !is Numeric })
			sortByDescending { it.degree }
		}.getDataList()
	}

	private fun List<DataSet>.simplifyPolynomials(): List<DataSet> = mapToPolynomialList().simplifyOnlyIterable()

	fun reduceList() =
		when {
			isEmpty -> SetNumber(0)
			listOfOperands.size == 1 -> listOfOperands.first()
			else -> this
		}
}