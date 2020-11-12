package models.math.dataset.numeric

import computorv1.models.PolynomialTerm
import globalextensions.*
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.Brackets
import models.math.dataset.DataSet
import models.math.dataset.Matrix

data class SetNumber(var number: Number = 0) : Numeric, Comparable<SetNumber> {

	override fun toString(): String = number.toString()

	operator fun plus(other: Number) = copy(number = number + other)
	operator fun plus(other: SetNumber) = this + other.number
	override fun plus(other: DataSet) =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')
			is Complex, is Brackets, is PolynomialTerm -> other + this
			else -> this + other as SetNumber
		}

	operator fun minus(other: Number) = copy(number = number - other)
	operator fun minus(other: SetNumber) = this - other.number
	override fun minus(other: DataSet) =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')
			is Complex -> other.copy(real = this - other.real, imaginary = -other.imaginary)
			is Brackets -> {
				other * PolynomialTerm(-1) + this
			}
			is PolynomialTerm -> other * SetNumber(-1) + this
			else -> this - other as SetNumber
		}

	operator fun times(other: Number) = copy(number = number * other)
	operator fun times(other: SetNumber) = this * other.number
	override fun times(other: DataSet) =
		if (other is SetNumber) this * other
		else other * this

	operator fun div(other: Number) = copy(number = number / other)
	operator fun div(other: SetNumber) = this / other.number
	override fun div(other: DataSet) =
		if (other is SetNumber) this / other
		else throw IllegalOperationException(this::class, other::class, '/')

	operator fun rem(other: Number) = copy(number = number % other)
	operator fun rem(other: SetNumber) = this % other.number
	override fun rem(other: DataSet) =
		if (other is SetNumber) this % other
		else throw IllegalOperationException(this::class, other::class, '%')

	override fun pow(other: DataSet): SetNumber {
		if (other !is SetNumber) throw IllegalOperationException(this::class, other::class, '^')
		return SetNumber(number.elevate(other.number))
	}

	operator fun compareTo(other: Number): Int = number.compareTo(other)
	override operator fun compareTo(other: SetNumber): Int = compareTo(other.number)

	operator fun unaryMinus() = this * -1
}