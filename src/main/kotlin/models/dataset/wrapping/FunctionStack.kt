package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import computorv1.simplify
import globalextensions.isEmpty
import globalextensions.mapToPolynomialList
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
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
			isEmpty -> other
			other.isEmpty() -> this
			else -> Brackets(this, other)
		}

	override fun minus(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, "-")
			isEmpty -> other * SetNumber(-1)
			other.isEmpty() -> this
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
		if (it is Function) "($it)" else it.toString()
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