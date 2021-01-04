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

class PolynomialTerm(
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
					PolynomialTerm(number = number + other.number, degree, name).tryCastToNumeric()
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
					PolynomialTerm(number - other.number, degree, name).tryCastToNumeric()
				} else {
					Brackets(
							this, PolynomialTerm(other.number * SetNumber(-1), other.degree, other.name)
					)
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

			is PolynomialTerm ->
				if (name == other.name)
					PolynomialTerm(number * other.number, degree = degree + other.degree, name).tryCastToNumeric()
				else
					FunctionStack(this, other)

			is Wrapping -> other * this

			is Function -> PolynomialTerm(FunctionStack(mutableListOf(number, other)), degree, name).tryCastToNumeric()

			else -> PolynomialTerm(number * other, degree, name).tryCastToNumeric()
		}

	override fun div(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "/")

			is PolynomialTerm -> PolynomialTerm(number / other.number, degree - other.degree, name)
					.tryCastToNumeric()

			is Wrapping -> PolynomialTerm(Fraction(this, other), degree, name)

			else -> PolynomialTerm(number / other, degree, name).tryCastToNumeric()
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, "%")

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(this::class, other::class, "^")

		return PolynomialTerm(number, other.number as Int * degree, name).tryCastToNumeric()
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

	override fun equals(other: Any?): Boolean =
		when (other) {
			null -> false
			!is PolynomialTerm -> false
			this === other -> true
			else -> number == other.number && degree == other.degree && name == other.name
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31 * result + number.hashCode()
		result = 31 * result + degree.hashCode()
		result = 31 * result + name.hashCode()
		return result
	}
}