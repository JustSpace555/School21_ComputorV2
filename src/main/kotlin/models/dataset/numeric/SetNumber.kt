package models.dataset.numeric

import computorv1.models.PolynomialTerm
import globalextensions.*
import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.wrapping.Fraction
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

data class SetNumber(var number: Number = 0) : Numeric, Comparable<SetNumber> {

	override fun toString(): String = number.toString()

	operator fun plus(other: Number) = copy(number = number + other)
	operator fun plus(other: SetNumber) = this + other.number
	override fun plus(other: DataSet) =
		when (other) {
			is Matrix -> throw IllegalOperationException(SetNumber::class, Matrix::class, '+')
			is SetNumber -> this + other
			else -> other + this
		}

	operator fun minus(other: Number) = copy(number = number - other)
	operator fun minus(other: SetNumber) = this - other.number
	override fun minus(other: DataSet) =
		when (other) {
			is Matrix -> throw IllegalOperationException(SetNumber::class, Matrix::class, '-')
			is SetNumber -> this - other
			else -> other * SetNumber(-1) + this
		}

	operator fun times(other: Number) = copy(number = number * other)
	operator fun times(other: SetNumber) = this * other.number
	override fun times(other: DataSet) =
		if (other is SetNumber) this * other
		else other * this

	operator fun div(other: Number) = copy(number = number / other)
	operator fun div(other: SetNumber) = this / other.number
	override fun div(other: DataSet) =
		when(other) {
			is Matrix -> throw IllegalOperationException(SetNumber::class, other::class, '/')
			is SetNumber -> this / other
			is PolynomialTerm -> {
				other.copy(number =
					if (other.number is SetNumber) {
						this / other.number
					} else {
						Fraction(this, other.number)
					},
					degree = other.degree * -1
				)
			}

			else -> Fraction(this, other)
		}

	operator fun rem(other: Number) = copy(number = number % other)
	operator fun rem(other: SetNumber) = this % other.number
	override fun rem(other: DataSet) =
		if (other is SetNumber) this % other
		else throw IllegalOperationException(SetNumber::class, other::class, '%')

	override fun pow(other: DataSet): SetNumber {
		if (other !is SetNumber) throw IllegalOperationException(SetNumber::class, other::class, '^')
		return SetNumber(number.elevate(other.number))
	}

	operator fun compareTo(other: Number): Int = number.compareTo(other)
	override operator fun compareTo(other: SetNumber): Int = compareTo(other.number)

	operator fun unaryMinus() = this * -1
}