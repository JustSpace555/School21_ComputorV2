package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import globalextensions.isEmpty
import globalextensions.mapToPolynomialList
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class FunctionStack(override val listOfOperands: List<DataSet> = listOf()) : Wrapping() {

	constructor(vararg elements: DataSet) : this(elements.toList())

	override val isEmpty: Boolean = listOfOperands.isEmpty()

	override fun plus(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, "+")
			isEmpty && other.isEmpty() -> SetNumber()
			isEmpty || other.isEmpty() -> if (isEmpty) other else this

			other is Function -> {
				if (listOfOperands.find { it == other || it is PolynomialTerm && it.number == other } == null)
					Brackets(this, other)
				else when (val number = listOfOperands.find { it is Numeric }) {
					null -> {
						val newList = listOfOperands.toMutableList()
						newList.add(0, SetNumber(2))
						FunctionStack(newList)
					}
					else -> {
						FunctionStack(
							listOf(number + SetNumber(1)) + listOfOperands.filter { it != number }
						)
					}
				}
			}

			other is FunctionStack -> {
				if (other == this) {
					if (listOfOperands.filterIsInstance<Numeric>().isNotEmpty()) {
						val number = listOfOperands.first { it is Numeric }
						val newList = listOfOperands.toMutableList()
						newList.remove(number)
						FunctionStack(listOf(number * SetNumber(2)) + newList)
					} else {
						FunctionStack(listOf(SetNumber(2)) + listOfOperands)
					}
				} else {
					val thisNumber = listOfOperands.firstOrNull { it is Numeric }
					val otherNumber = other.listOfOperands.firstOrNull { it is Numeric }
					when {
						thisNumber == null && otherNumber == null -> Brackets(this, other)
						thisNumber == null -> {
							val newList = other.listOfOperands.toMutableList()
							newList.remove(otherNumber)
							if (
									listOfOperands.mapToPolynomialList()
											.zip(newList.mapToPolynomialList())
											.all { (el1, el2) -> el1 == el2 }
							) {
								FunctionStack(listOf(otherNumber!! + SetNumber(1)) + newList)
							} else {
								Brackets(this, other)
							}
						}
						otherNumber == null -> {
							val newList = listOfOperands.toMutableList()
							newList.remove(thisNumber)
							if (
									other.listOfOperands.mapToPolynomialList()
											.zip(newList.mapToPolynomialList())
											.all { (el1, el2) -> el1 == el2 }
							) {
								FunctionStack(listOf(thisNumber + SetNumber(1)) + newList)
							} else {
								Brackets(this, other)
							}
						}
						else -> {
							val newThisList = listOfOperands.toMutableList()
							val newOtherList = other.listOfOperands.toMutableList()
							newThisList.remove(thisNumber)
							newOtherList.remove(otherNumber)
							if (
								newThisList.mapToPolynomialList()
								.zip(newOtherList.mapToPolynomialList())
								.all { (el1, el2) -> el1 == el2 }
							) {
								FunctionStack(listOf(thisNumber + otherNumber) + newThisList)
							} else {
								Brackets(this, other)
							}
						}
					}
				}
			}

			else -> Brackets(this, other)
		}

	override fun minus(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, "-")
			isEmpty && other.isEmpty() -> SetNumber()
			isEmpty || other.isEmpty() -> if (isEmpty) other * SetNumber(-1) else this

			other is Function -> {
				if (listOfOperands.find { it == other || it is PolynomialTerm && it.number == other } == null)
					Brackets(this, other * SetNumber(-1))
				else when (val number = listOfOperands.find { it is Numeric }) {
					null -> {
						val newList = listOfOperands.toMutableList()
						newList.add(0, SetNumber(-1))
						FunctionStack(newList)
					}
					else -> {
						FunctionStack(
							listOf(number - SetNumber(1)) + listOfOperands.filter { it != number }
						)
					}
				}
			}

			other is FunctionStack -> {
				if (other == this) {
					SetNumber()
				} else {
					val thisNumber = listOfOperands.firstOrNull { it is Numeric }
					val otherNumber = other.listOfOperands.firstOrNull { it is Numeric }
					when {
						thisNumber == null && otherNumber == null -> Brackets(this, other * SetNumber(-1))
						thisNumber == null -> {
							val newList = other.listOfOperands.toMutableList()
							newList.remove(otherNumber)
							if (
									listOfOperands.mapToPolynomialList()
											.zip(newList.mapToPolynomialList())
											.all { (el1, el2) -> el1 == el2 }
							) {
								FunctionStack(listOf(otherNumber!! * SetNumber(-1) + SetNumber(1)) + newList)
							} else {
								Brackets(this, other * SetNumber(-1))
							}
						}
						otherNumber == null -> {
							val newList = listOfOperands.toMutableList()
							newList.remove(thisNumber)
							if (
									other.listOfOperands.mapToPolynomialList()
											.zip(newList.mapToPolynomialList())
											.all { (el1, el2) -> el1 == el2 }
							) {
								FunctionStack(listOf(thisNumber - SetNumber(1)) + newList)
							} else {
								Brackets(this, other * SetNumber(-1))
							}
						}
						else -> {
							val newThisList = listOfOperands.toMutableList()
							val newOtherList = other.listOfOperands.toMutableList()
							newThisList.remove(thisNumber)
							newOtherList.remove(otherNumber)
							if (
									newThisList.mapToPolynomialList()
											.zip(newOtherList.mapToPolynomialList())
											.all { (el1, el2) -> el1 == el2 }
							) {
								FunctionStack(listOf(thisNumber - otherNumber) + newThisList)
							} else {
								Brackets(this, other * SetNumber(-1))
							}
						}
					}
				}
			}

			else -> Brackets(this, other * SetNumber(-1))
		}

	override fun times(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, "*")

			isEmpty || other.isEmpty() -> SetNumber()

			other is SetNumber && other.compareTo(1.0) == 0 -> this

			(other is Numeric || other is PolynomialTerm) &&
			listOfOperands.any { it is Numeric || it is PolynomialTerm } -> {
				val newList = listOfOperands.toMutableList()
				val temp = newList.removeAt(newList.indexOfFirst { it is Numeric || it is PolynomialTerm})

				FunctionStack(listOf(temp * other) + newList).simplify()
			}

			other is FunctionStack -> FunctionStack(
				(listOfOperands + other.listOfOperands).simplifyOnlyIterable()
			).simplify()

			else -> FunctionStack(listOfOperands + other).simplify()
		}

	override fun div(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, "/")

			isEmpty -> SetNumber()

			other.isEmpty() -> throw DivideByZeroException()
			other is SetNumber && other.compareTo(1.0) == 0 -> this

			(other is Numeric || other is PolynomialTerm) &&
			listOfOperands.any { it is Numeric || it is PolynomialTerm} -> {
				val newList = listOfOperands.toMutableList()
				val temp = newList.removeAt(newList.indexOfFirst { it is Numeric || it is PolynomialTerm })

				FunctionStack(listOf(temp / other) + newList).simplify()
			}

			other is Fraction -> Fraction(this * other.denominator, other.numerator)

			else -> Fraction(this, other).simplify()
	}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(FunctionStack::class, other::class, "%")

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number is Double)
			throw IllegalOperationException(FunctionStack::class, other::class, "%")

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when (other.number as Int) {
			0 -> SetNumber(1)
			1 -> this
			else -> {
				var newFunctionStack = this as DataSet
				repeat(number - 1) { newFunctionStack *= newFunctionStack }
				if (belowZero)
					Fraction(
						SetNumber(1), newFunctionStack.apply { if (this is FunctionStack) this.simplify() }
					).simplify()
				else
					newFunctionStack.apply { if (this is FunctionStack) this.simplify() }
			}
		}
	}

	private fun simplify() =
		when {
			isEmpty -> SetNumber()
			listOfOperands.size == 1 -> listOfOperands.first()
			listOfOperands.size == 2 && listOfOperands.filterIsInstance<Numeric>().isNotEmpty() &&
					(
							listOfOperands.first { it is Numeric } == SetNumber(1) ||
							listOfOperands.first { it is Numeric } == Complex(1, 0)
					) -> listOfOperands.first { it !is Numeric }
			else -> this
		}

	private fun List<DataSet>.simplifyOnlyIterable(): List<DataSet> {
		val filtered = this.filter { it is PolynomialTerm || it is Numeric }.also { if (it.isEmpty()) return this }

		var outputNumber = SetNumber(1) as DataSet
		filtered.forEach { outputNumber *= it }

		return listOf(outputNumber) + this@simplifyOnlyIterable.filter { it !is PolynomialTerm && it !is Numeric }
	}



	override fun toString(): String = listOfOperands.joinToString(" * ") {
		when (it) {
			is Function -> "($it)"
			is Complex -> if (it.real.isNotZero() && it.imaginary.isNotZero()) "($it)" else it.toString()
			else -> it.toString()
		}
	}

	override fun equals(other: Any?): Boolean =
		when {
			other == null -> false
			other !is FunctionStack -> false
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
}