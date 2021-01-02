package computorv1.models

import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.Function
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.Fraction
import models.dataset.wrapping.FunctionStack
import models.dataset.wrapping.Wrapping
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

data class PolynomialTerm(
	val number: DataSet = SetNumber(),
	var degree: Int = 0,
	val name: String = "X"
) : DataSet {

	constructor(number: Number, degree: Int = 0, name: String = "X") : this(SetNumber(number), degree, name)

	init {
		if (number is PolynomialTerm) throw IllegalOperationException(PolynomialTerm::class, PolynomialTerm::class)
	}

	override fun plus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "+")

			is PolynomialTerm -> {
				if (other.degree == degree) {
					copy(number = number + other.number).tryCastToNumeric()
				} else {
					Brackets(this, other)
				}
			}

			is Wrapping -> other + this

			else -> {
				if (degree == 0) {
					number + other
				} else {
					Brackets(this, other)
				}
			}
		}

	override fun minus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "-")

			is PolynomialTerm -> {
				if (other.degree == degree) {
					copy(number = number - other.number).tryCastToNumeric()
				} else {
					Brackets(this, other.copy(number = other.number * SetNumber(-1)))
				}
			}

			is Wrapping -> other * SetNumber(-1) + this

			else -> {
				if (degree == 0) {
					number - other
				} else {
					Brackets(this, other * SetNumber(-1))
				}
			}
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "*")

			is PolynomialTerm -> copy(number = number * other.number, degree = degree + other.degree).tryCastToNumeric()

			is Wrapping -> other * this

			is Function -> copy(number = FunctionStack(mutableListOf(number, other))).tryCastToNumeric()

			else -> copy(number = number * other).tryCastToNumeric()
		}

	override fun div(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "/")

			is PolynomialTerm -> copy(number = number / other.number, degree = degree - other.degree).tryCastToNumeric()

			is Wrapping -> other * this

			else -> copy(number = number / other).tryCastToNumeric()
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, "%")

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(this::class, other::class, "^")

		return copy(degree = other.number as Int * degree).tryCastToNumeric()
	}

	override fun toString(): String {
		val numberStr = when(number) {
			is Complex -> { if (number.real.isZero() || number.imaginary.isZero()) number.toString() else "($number)" }
			is Function -> "($number)"
			else -> number.toString()
		}

		return "$numberStr * $name^$degree"
	}

	private fun tryCastToNumeric(): DataSet = if (degree == 0) number else this
}