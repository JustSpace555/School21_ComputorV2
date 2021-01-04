package models.dataset.numeric

import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.Fraction
import models.dataset.wrapping.FunctionStack
import models.dataset.wrapping.Wrapping
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

class Complex(var real: SetNumber = SetNumber(0), var imaginary: SetNumber): Numeric {

	constructor(real: Number = 0, imaginary: Number) : this(SetNumber(real), SetNumber(imaginary))

	override fun plus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "+")

			is Complex -> Complex(real + other.real, imaginary + other.imaginary).tryCastToSetNumber()

			is Wrapping, is PolynomialTerm -> other + this

			is Function -> Brackets(other, this)

			else -> Complex(real + other as SetNumber, imaginary)
		}

	override fun minus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "-")

			is Complex -> Complex(real - other.real, imaginary - other.imaginary).tryCastToSetNumber()

			is Wrapping, is PolynomialTerm -> other * SetNumber(-1) + this

			is Function -> Brackets(this, other * SetNumber(-1))

			else -> Complex(real - other as SetNumber, imaginary)
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix, is Brackets -> other * this

			is Complex -> Complex(
					real * other.real - imaginary * other.imaginary,
				real * other.imaginary + other.real * imaginary
			).tryCastToSetNumber()

			is Wrapping, is PolynomialTerm -> other * this

			is Function -> FunctionStack(other, this)

			else -> {
				other as SetNumber
				Complex(real * other, imaginary * other).tryCastToSetNumber()
			}
		}

	override fun div(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "/")

			is Complex -> {
				val square = other.real * other.real + other.imaginary * other.imaginary
				Complex(
					(real * other.real + imaginary * other.imaginary) / square,
					(imaginary * other.real - real * other.imaginary) / square
				).tryCastToSetNumber()
			}

			is Wrapping, is PolynomialTerm, is Function -> Fraction(this, other)

			else -> {
				other as SetNumber
				Complex(real / other, imaginary / other).tryCastToSetNumber()
			}
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, "%")

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(this::class, other::class, "^")

		var degree = other.number as Int
		if (degree == 0) return SetNumber(1)

		val belowZero = degree < 0
		if (belowZero) degree *= -1

		var newComplex = this as DataSet
		repeat(degree - 1) { newComplex *= newComplex }

		return if (belowZero) Fraction(SetNumber(1), newComplex) else newComplex
	}

	override fun toString(): String {

		val toAllString: SetNumber.() -> String = {
			when(number) {
				1, 1.0 -> ""
				-1, -1.0 -> "-"
				else -> this.toString()
			}
		}

		if (real.isZero())
			return "${imaginary.toAllString()}i"

		if (imaginary.isZero())
			return real.toString()

		var tempIm = imaginary
		val signString = if (imaginary < 0) {
			tempIm *= -1
			" - "
		} else {
			" + "
		}
		return "$real$signString${tempIm.toAllString()}i"
	}

	private fun tryCastToSetNumber(): Numeric = if (imaginary.isZero()) real else this

	override fun equals(other: Any?): Boolean =
		when (other) {
			null -> false
			!is Complex -> false
			this === other -> true
			else -> real == other.real && imaginary == other.imaginary
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31 * result + real.hashCode()
		result = 31 * result + imaginary.hashCode()
		return result
	}
}