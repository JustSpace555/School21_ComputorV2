package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import computorv1.reducedString
import computorv1.simplify
import globalextensions.*
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class Brackets(_input: List<DataSet> = listOf()): Wrapping() {

	override val listOfOperands: List<DataSet> = _input.flatMap { it.getBracketList() }.simplifyDataSet()

	constructor(vararg elements: DataSet) : this(elements.toList())

	override val isEmpty: Boolean = listOfOperands.isEmpty()

	override fun plus(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(Brackets::class, Matrix::class, "+")
			isEmpty && other.isEmpty() -> SetNumber()
			isEmpty || other.isEmpty() -> if (isEmpty) other else this
			other is Brackets -> addElements(other.listOfOperands)
			else -> addElements(other)
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

					Brackets(newFunctionList.flatMap { it.getBracketList() }.simplifyDataSet()).reduceList()
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
				is Wrapping -> Fraction(this, other)

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
		//TODO переделать условие и добавить отдельную проверку для Double
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(Brackets::class, other::class, "^")

		if (isEmpty) return SetNumber()

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when(number) {
			0 -> SetNumber(1)
			1 -> if (belowZero) Fraction(SetNumber(1), this) else this
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
				val pairList = listOfOperands.mapToPolynomialList().zip(other.listOfOperands.mapToPolynomialList())
				pairList.all { (el1, el2) -> el1 == el2 }
			}
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31 * result + listOfOperands.hashCode()
		return result
	}

	private fun addElements(elements: List<DataSet>) =
		Brackets((listOfOperands + elements).simplifyDataSet()).reduceList()

	private fun addElements(vararg elements: DataSet) =
		Brackets((listOfOperands + elements.toList()).simplifyDataSet()).reduceList()

	private fun applyOnEachElement(
		function: (DataSet, DataSet) -> DataSet,
		element: DataSet
	): DataSet {

		if (element is SetNumber && element.compareTo(1.0) == 0) return this

		//TODO Мб лушче List<DataSet> ?
		val newElementsList = mutableListOf<PolynomialTerm>().apply {
			listOfOperands.forEach {
				when (val newElement = function(it, element)) {
					is Brackets -> addAll(newElement.listOfOperands.mapToPolynomialList())
					else -> add(newElement.toPolynomial())
				}
			}
		}.simplifyDataSet()

		return Brackets(newElementsList).reduceList()
	}
	
	private fun List<DataSet>.simplifyDataSet(): List<DataSet> {
		val newList = mutableListOf<DataSet>()
		val mapOfFunctions = mutableMapOf<Function, DataSet>()
		val mapOfPolynomials = mutableMapOf<PolynomialTerm, DataSet>()

		forEach {
			when (it) {
				is Function -> {
					if (mapOfFunctions.containsKey(it)) mapOfFunctions[it] = mapOfFunctions[it]!! + SetNumber(1)
					else mapOfFunctions[it] = SetNumber(1)
				}
				is FunctionStack -> {
					val foundFunction = it.listOfOperands.find { ffs -> ffs is Function } as Function?
					if (it.listOfOperands.size == 2 && foundFunction != null) {
						if (mapOfFunctions.containsKey(foundFunction)) {
							mapOfFunctions[foundFunction] =
									mapOfFunctions[foundFunction]!! +
									it.listOfOperands.first { ffs -> ffs !is Function }
						} else {
							mapOfFunctions[foundFunction] = it.listOfOperands.first { ffs -> ffs !is Function }
						}
					} else newList.add(it)
				}
				is PolynomialTerm -> {
					mapOfPolynomials[it] = mapOfPolynomials[it]?.plus(SetNumber(1)) ?: SetNumber(1)
				}
				else -> newList.add(it)
			}
		}

		mapOfFunctions.forEach { (function, number) ->
			if (number.isNotEmpty()) newList.add(FunctionStack(number, function))
		}
		mapOfPolynomials.forEach { (pol, number) ->
			newList.add(PolynomialTerm(number * pol.number, pol.degree, pol.name))
		}

		val isIterable: DataSet.() -> Boolean = { this is Numeric || this is PolynomialTerm && this.number is Numeric }

		val filtered = newList
			.filter { it.isIterable() }
			.also { if (it.isEmpty()) return newList }
			.mapToPolynomialList()
			.simplify()
			.flatMap { if (it.degree == 0) it.number.getBracketList() else listOf(it) }

		return mutableListOf<DataSet>().apply {
			filtered.forEach {
				this.addAll(if (it is Brackets) it.listOfOperands else listOf(it))
			}
			addAll(newList.filter { !it.isIterable() })
			sortByDescending { if (it is PolynomialTerm) it.degree else 0 }
		}
	}

	fun reduceList() =
		when {
			isEmpty -> SetNumber(0)
			listOfOperands.size == 1 -> listOfOperands.first()
			else -> this
		}
}