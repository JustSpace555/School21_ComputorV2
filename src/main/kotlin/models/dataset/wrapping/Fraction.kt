package models.dataset.wrapping

import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.calcexception.variable.NotEqualDenominatorException

data class Fraction(val numerator: DataSet, val denominator: DataSet) : Wrapping() {

	init {
		if (denominator is Numeric && denominator.isZero()) throw DivideByZeroException()
	}

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> {
				if (denominator != other.denominator) throw NotEqualDenominatorException(this, other, '+')
				copy(numerator = numerator + other.numerator).simplify()
			}
			else -> {
				if (other is Numeric && other.isZero())
					this
				else
					copy(numerator = numerator + other * denominator).simplify()
			}
		}

	override fun minus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> {
				if (denominator != other.denominator) throw NotEqualDenominatorException(this, other, '-')
				copy(numerator = numerator - other.numerator).simplify()
			}
			else -> {
				if (other is Numeric && other.isZero())
					this
				else
					copy(numerator = numerator - other * denominator).simplify()
			}
	}

	override fun times(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> Fraction(numerator * other.numerator, denominator * other.denominator)
				.simplify()

			is PolynomialTerm -> other.copy(number = this * other.number)

			else -> when {
				other is Numeric && other.isZero() -> SetNumber(0)
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> copy(numerator = numerator * other).simplify()
			}
		}

	override fun div(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(Fraction::class, Matrix::class)
			is Fraction -> Fraction(numerator * other.denominator, denominator * other.numerator)
				.simplify()

			is PolynomialTerm -> other.copy(number = this / other.number)

			else -> when {
				other is Numeric && other.isZero() -> throw DivideByZeroException()
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> copy(numerator = denominator * other).simplify()
			}
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(Fraction::class, other::class, '%')

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(Fraction::class, other::class, '^')

		var number = other.number as Int
		val belowZero = number < 0
		if (belowZero) number *= -1

		return when(number) {
			0 -> SetNumber(1)
			1 -> this
			else -> {
				var newFraction = this
				repeat(number - 1) { newFraction = (newFraction * newFraction) as Fraction }
				return if (belowZero) {
					val simpled = newFraction.simplify()
					if (simpled is SetNumber) {
						Fraction(SetNumber(1), simpled)
					} else {
						simpled as Fraction
						Fraction(simpled.denominator, simpled.numerator)
					}

				} else newFraction.simplify()
			}
		}
	}

	override fun toString(): String = "($numerator / $denominator)"

	private fun simplify(): DataSet =
		when {
			numerator == denominator -> SetNumber(1)
			!(numerator is SetNumber && denominator is SetNumber) -> this
			!(numerator.number is Int && denominator.number is Int) -> this
			else -> {
				var f1Number = numerator.number as Int
				var f2Number = denominator.number as Int

				while (f1Number != f2Number && f1Number > 0 && f2Number > 0) {
					if (f1Number > f2Number) f1Number -= f2Number else f2Number -= f1Number
				}
				SetNumber(f1Number)
			}
		}
}