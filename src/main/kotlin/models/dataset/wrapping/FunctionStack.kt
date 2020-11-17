package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class FunctionStack(override val listOfOperands: List<DataSet> = listOf()) : Wrapping() {

	constructor(vararg elements: DataSet) : this(elements.toList())

	override fun plus(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, '+')
			other is Numeric && other.isZero() -> this
			else -> Brackets(this, other)
		}

	override fun minus(other: DataSet): DataSet =
		when {
			other is Matrix -> throw IllegalOperationException(FunctionStack::class, Matrix::class, '-')
			other is Numeric && other.isZero() -> this
			else -> Brackets(this, other * SetNumber(-1))
		}

	override fun times(other: DataSet): DataSet {
		if (other is Matrix) throw IllegalOperationException(FunctionStack::class, Matrix::class, '*')

		if (other is Numeric && other.isZero()) return other
		else if (other is SetNumber && other.compareTo(1.0) == 0) return this

		if (
			(other is Numeric || other is PolynomialTerm) &&
			listOfOperands.any { it is Numeric || it is PolynomialTerm }
		) {
			val newList = listOfOperands.toMutableList()
			val temp = newList.removeAt(newList.indexOfFirst { it is Numeric || it is PolynomialTerm})
			
			return FunctionStack(listOf(temp * other) + newList)
		}

		return FunctionStack(listOfOperands + other)
	}

	override fun div(other: DataSet): DataSet {
		if (other is Matrix) throw IllegalOperationException(FunctionStack::class, Matrix::class, '/')

		if (other is Numeric && other.isZero()) throw DivideByZeroException()
		if (other is SetNumber && other.compareTo(1.0) == 0) return this

		if (
			(other is Numeric || other is PolynomialTerm) &&
			listOfOperands.any { it is Numeric || it is PolynomialTerm}
		) {
			val newList = listOfOperands.toMutableList()
			val temp = newList.removeAt(newList.indexOfFirst { it is Numeric || it is PolynomialTerm })

			return FunctionStack(listOf(temp / other) + newList)
		}

		return Fraction(this, other)
	}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(FunctionStack::class, other::class, '%')

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number is Double)
			throw IllegalOperationException(FunctionStack::class, other::class, '%')

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when (other.number as Int) {
			0 -> SetNumber(1)
			1 -> this
			else -> if (belowZero)
				Fraction(SetNumber(1), PolynomialTerm(this, number))
			else
				PolynomialTerm(this, number)
		}
	}

	override fun toString(): String = listOfOperands.joinToString(" * ")

}