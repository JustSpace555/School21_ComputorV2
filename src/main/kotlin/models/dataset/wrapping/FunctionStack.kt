package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import computorv1.simplify
import globalextensions.isEmpty
import globalextensions.mapToPolynomialList
import globalextensions.minus
import globalextensions.plus
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import javax.xml.crypto.Data

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