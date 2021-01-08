package models.dataset.numeric

import computation.samplePow
import computorv1.models.PolynomialTerm
import globalextensions.*
import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.wrapping.Fraction
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class SetNumber(var number: Number = 0) : Numeric, Comparable<SetNumber> {

	override fun toString(): String = number.toString()
	override fun equals(other: Any?): Boolean =
		when (other) {
			null -> false
			!is SetNumber -> false
			this === other -> true
			else -> number == other.number
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31 * result + number.hashCode()
		return result
	}

	operator fun plus(other: Number) = SetNumber(number + other)
	operator fun plus(other: SetNumber) = this + other.number
	override fun plus(other: DataSet) =
		when (other) {
			is Matrix -> throw IllegalOperationException(SetNumber::class, Matrix::class, "+")
			is SetNumber -> this + other
			else -> other + this
		}

	operator fun minus(other: Number) = SetNumber(number = number - other)
	operator fun minus(other: SetNumber) = this - other.number
	override fun minus(other: DataSet) =
		when (other) {
			is Matrix -> throw IllegalOperationException(SetNumber::class, Matrix::class, "-")
			is SetNumber -> this - other
			else -> other * SetNumber(-1) + this
		}

	operator fun times(other: Number) = SetNumber(number = number * other)
	operator fun times(other: SetNumber) = this * other.number
	override fun times(other: DataSet) =
		if (other is SetNumber) this * other
		else other * this

	operator fun div(other: Number) = SetNumber(number = number / other)
	operator fun div(other: SetNumber) = this / other.number
	override fun div(other: DataSet) =
		when(other) {
			is Matrix -> throw IllegalOperationException(SetNumber::class, Matrix::class, "/")
			is Complex -> {
				if (other.imaginary.isZero()) {
					this / other.real
				} else {
					throw IllegalOperationException(SetNumber::class, Complex::class, "/")
				}
			}
			is SetNumber -> this / other
			is PolynomialTerm -> {
				PolynomialTerm(number =
					if (other.number is SetNumber) {
						this / other.number
					} else {
						Fraction(this, other.number)
					}, other.degree * -1, other.name
				)
			}

			else -> Fraction(this, other)
		}

	operator fun rem(other: Number) = SetNumber(number = number % other)
	operator fun rem(other: SetNumber) = this % other.number
	override fun rem(other: DataSet) =
		if (other is SetNumber) this % other
		else throw IllegalOperationException(SetNumber::class, other::class, "%")

	override fun pow(other: DataSet): SetNumber {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(SetNumber::class, other::class, "^")
		return SetNumber(number.toDouble().samplePow(other.number as Int).tryCastToInt())
	}

	operator fun compareTo(other: Number): Int = number.compareTo(other)
	override operator fun compareTo(other: SetNumber): Int = compareTo(other.number)
}