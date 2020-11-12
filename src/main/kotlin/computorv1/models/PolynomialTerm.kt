package computorv1.models

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.Brackets
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber

data class PolynomialTerm(
	val number: DataSet = SetNumber(),
	var degree: Int = 0,
	val name: String = "X"
) : DataSet {

	constructor(number: Number, degree: Int = 0, name: String = "") : this(SetNumber(number), degree, name)

	override fun plus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')

			is PolynomialTerm -> {
				if (other.degree == degree) {
					copy(number = number + other.number)
				} else {
					Brackets(this, other)
				}
			}

			is Brackets -> other + this

			else -> {
				if (degree == 0) {
					copy(number = number + other)
				} else {
					Brackets(this, PolynomialTerm(other))
				}
			}
		}

	override fun minus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')

			is PolynomialTerm -> {
				if (other.degree == degree) {
					copy(number = number - other.number)
				} else {
					Brackets(this, other.copy(number = other.number * SetNumber(-1)))
				}
			}

			is Brackets -> other - this

			else -> {
				if (degree == 0) {
					copy(number = (number + other) as Numeric)
				} else {
					Brackets(this, PolynomialTerm(other * SetNumber(-1)))
				}
			}
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '*')

			is PolynomialTerm -> copy(number = number * other.number, degree = degree + other.degree)

			is Brackets -> other * this

			is Function -> copy(number = Brackets(PolynomialTerm(number), PolynomialTerm(other)))

			else -> copy(number = number * other)
		}

	override fun div(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')

			is PolynomialTerm -> copy(number = number / other.number, degree = degree - other.degree)

			is Brackets -> throw IllegalOperationException(this::class, Brackets::class, '/')

			else -> copy(number = number / other)
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, '%')

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number is Double)
			throw IllegalOperationException(this::class, other::class, '^')

		return copy(degree = other.number as Int + degree)
	}

	override fun toString(): String {
		val numberStr = if (number is Complex) {
			if (number.real.isZero() || number.imaginary.isZero()) number.toString() else "($number)"
		} else {
			number.toString()
		}

		return "$numberStr * $name^$degree"
	}

	fun tryCastToNumeric(): DataSet = if (degree == 0) number else this

	operator fun invoke(): Numeric = number.pow(degree) as Numeric
	operator fun invoke(input: DataSet): Numeric = input.pow(degree) as Numeric
}