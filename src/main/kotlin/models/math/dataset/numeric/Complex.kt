package models.math.dataset.numeric

import models.exception.calcexception.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix

data class Complex(var real: SetNumber = SetNumber(0), var imaginary: SetNumber): Numeric {

	override fun plus(other: DataSet): Complex =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')
			is Function -> TODO()
			is Complex -> copy(
				real = real + other.real,
				imaginary = imaginary + other.imaginary
			)
			else -> copy(real = real + other as SetNumber)
		}

	override fun minus(other: DataSet): Complex =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')
			is Function -> TODO()
			is Complex -> copy(
				real = real - other.real,
				imaginary = imaginary - other.imaginary
			)
			else -> copy(real = real - other as SetNumber)
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix -> TODO()
			is Function -> TODO()
			is Complex -> copy(
				real = real * other.real - imaginary * other.imaginary,
				imaginary = real * other.imaginary + other.real * imaginary
			)
			else -> copy(real = real * other as SetNumber)
		}

	override fun div(other: DataSet): Complex =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')
			is Function -> TODO()
			is Complex -> {
				val square = other.real * other.real + other.imaginary * other.imaginary
				copy(
					real = (real * other.real + imaginary * other.imaginary) / square,
					imaginary = (imaginary * other.real - real * other.imaginary) / square
				)
			}
			else -> copy(real = real / other as SetNumber)
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, '%')

	override fun toString(): String {
		if (real.number == 0.0)
			return "${imaginary}i"

		var tempIm = imaginary
		val signString = if (imaginary < 0) {
			tempIm *= -1
			" - "
		} else {
			" + "
		}
		return "$real$signString${tempIm}i"
	}

}