package models.math.dataset.numeric

import globalextensions.compareTo
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Matrix

data class Complex(var real: SetNumber = SetNumber(0), var imaginary: SetNumber): Numeric {

	constructor(real: Number = 0, imaginary: Number) : this(SetNumber(real), SetNumber(imaginary))

	override fun plus(other: DataSet): Numeric =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')
			is Complex -> copy(
				real = real + other.real,
				imaginary = imaginary + other.imaginary
			).tryCastToSetNumber()
			else -> copy(real = real + other as SetNumber)
		}

	override fun minus(other: DataSet): Numeric =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')
			is Complex -> copy(
				real = real - other.real,
				imaginary = imaginary - other.imaginary
			).tryCastToSetNumber()
			else -> copy(real = real - other as SetNumber)
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix -> other * this
			is Complex -> copy(
				real = real * other.real - imaginary * other.imaginary,
				imaginary = real * other.imaginary + other.real * imaginary
			).tryCastToSetNumber()
			else -> Complex(real * other as SetNumber, imaginary * other).tryCastToSetNumber()
		}

	override fun div(other: DataSet): Numeric =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')
			is Complex -> {
				val square = other.real * other.real + other.imaginary * other.imaginary
				copy(
					real = (real * other.real + imaginary * other.imaginary) / square,
					imaginary = (imaginary * other.real - real * other.imaginary) / square
				).tryCastToSetNumber()
			}
			else -> Complex(real / other as SetNumber, imaginary / other).tryCastToSetNumber()
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, '%')

	override fun toString(): String {
		if (real.number.compareTo(0) == 0)
			return "${imaginary}i"

		if (imaginary.compareTo(0) == 0)
			return real.toString()

		var tempIm = imaginary
		val signString = if (imaginary < 0) {
			tempIm *= -1
			" - "
		} else {
			" + "
		}
		return "$real$signString${tempIm}i"
	}

	private fun tryCastToSetNumber(): Numeric = if (imaginary.compareTo(0) == 0) real else this
}