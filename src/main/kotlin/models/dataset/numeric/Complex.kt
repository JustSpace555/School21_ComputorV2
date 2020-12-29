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

data class Complex(var real: SetNumber = SetNumber(0), var imaginary: SetNumber): Numeric {

	constructor(real: Number = 0, imaginary: Number) : this(SetNumber(real), SetNumber(imaginary))

	override fun plus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "+")

			is Complex -> copy(
				real = real + other.real,
				imaginary = imaginary + other.imaginary
			).tryCastToSetNumber()

			is Wrapping -> other + this

			is Function -> Brackets(other, this)

			else -> copy(real = real + other as SetNumber)
		}

	override fun minus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, "-")

			is Complex -> copy(
				real = real - other.real,
				imaginary = imaginary - other.imaginary
			).tryCastToSetNumber()

			is Wrapping -> other * PolynomialTerm(-1) + this

			is Function -> Brackets(this, other * SetNumber(-1))

			else -> copy(real = real - other as SetNumber)
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix, is Brackets -> other * this

			is Complex -> copy(
				real = real * other.real - imaginary * other.imaginary,
				imaginary = real * other.imaginary + other.real * imaginary
			).tryCastToSetNumber()

			is Wrapping -> other * this

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
				copy(
					real = (real * other.real + imaginary * other.imaginary) / square,
					imaginary = (imaginary * other.real - real * other.imaginary) / square
				).tryCastToSetNumber()
			}

			is Wrapping -> other / this

			is Function -> Fraction(this, other)

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
}