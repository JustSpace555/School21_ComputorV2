package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import globalextensions.isEmpty
import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class Fraction(val numerator: DataSet, val denominator: DataSet) : Wrapping() {

	override val isEmpty: Boolean

	init {
		if (denominator.isEmpty()) throw DivideByZeroException()
		isEmpty = numerator.isEmpty()
	}

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> {
				Fraction(
					numerator * other.denominator + other.numerator * denominator,
					denominator * other.denominator
				).simplify()
			}
			else -> {
				if (other.isEmpty())
					this
				else
					Fraction(numerator + other * denominator, denominator).simplify()
			}
		}

	override fun minus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> {
				Fraction(
					numerator * other.denominator - other.numerator * denominator,
					denominator * other.denominator
				).simplify()
			}
			else -> {
				if (other.isEmpty())
					this
				else
					Fraction(numerator - other * denominator, denominator).simplify()
			}
	}

	override fun times(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> Fraction(
				numerator * other.numerator,
				denominator * other.denominator
			).simplify()

			is PolynomialTerm -> other.copy(number = this * other.number)

			else -> when {
				other.isEmpty() -> SetNumber(0)
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> Fraction(numerator * other, denominator).simplify()
			}
		}

	override fun div(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> Fraction(
				numerator * other.denominator,
				denominator * other.numerator
			).simplify()

			is PolynomialTerm -> other.copy(number = this / other.number)

			else -> when {
				other.isEmpty() -> throw DivideByZeroException()
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> Fraction(numerator, denominator * other).simplify()
			}
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(Fraction::class, other::class, "%")

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(Fraction::class, other::class, "^")

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when(number) {
			0 -> SetNumber(1)
			1 -> this
			else -> {
				var newFraction = this as DataSet
				repeat(number - 1) { newFraction *= newFraction }
				return if (belowZero) {
					val simpled = newFraction.apply { if (this is Fraction) this.simplify() }
					if (simpled is SetNumber) {
						Fraction(SetNumber(1), simpled)
					} else {
						simpled as Fraction
						Fraction(simpled.denominator, simpled.numerator)
					}

				} else newFraction.apply { if (this is Fraction) this.simplify() }
			}
		}
	}

	override fun toString(): String = "(($numerator) / ($denominator))"

	fun simplify(): DataSet =
		when {
			numerator == denominator -> SetNumber(1)

			numerator !is SetNumber || denominator !is SetNumber -> this

			else -> SetNumber((numerator as Number).toDouble() / (denominator as Number).toDouble())
		}


	override fun hashCode(): Int {
		var result = 17
		result = 31 * result + numerator.hashCode()
		result = 31 * result + denominator.hashCode()
		return result
	}

	override fun equals(other: Any?) =
		when {
			other == null -> false
			other !is Fraction -> false
			this === other -> true
			else -> numerator == other.numerator && denominator == other.denominator
		}
}