package models.math.dataset.numeric

import models.exception.calcexception.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix

data class Complex(var real: SetNumber = SetNumber(0), var imaginary: SetNumber): Numeric {

	override fun plus(input: DataSet): Complex =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')
			is Function -> TODO()
			is Complex -> copy(
				real = real + input.real,
				imaginary = imaginary + input.imaginary
			)
			else -> copy(real = real + input as SetNumber)
		}

	override fun minus(input: DataSet): Complex =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')
			is Function -> TODO()
			is Complex -> copy(
				real = real - input.real,
				imaginary = imaginary - input.imaginary
			)
			else -> copy(real = real - input as SetNumber)
		}

	override fun times(input: DataSet): DataSet =
		when (input) {
			is Matrix -> TODO()
			is Function -> TODO()
			is Complex -> copy(
				real = real * input.real - imaginary * input.imaginary,
				imaginary = real * input.imaginary + input.real * imaginary
			)
			else -> copy(real = real * input as SetNumber)
		}

	override fun div(input: DataSet): Complex =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')
			is Function -> TODO()
			is Complex -> {
				val square = input.real * input.real + input.imaginary * input.imaginary
				copy(
					real = (real * input.real + imaginary * input.imaginary) / square,
					imaginary = (imaginary * input.real - real * input.imaginary) / square
				)
			}
			else -> copy(real = real / input as SetNumber)
		}

	override fun rem(input: DataSet) = throw IllegalOperationException(this::class, input::class, '%')

	override fun toString(): String {
		if (real.number == 0.0)
			return "${imaginary}i"

		var tempIm = imaginary.number
		val signString = if (imaginary < 0) {
			tempIm *= -1
			" - "
		} else {
			" + "
		}
		return "$real$signString${tempIm}i"
	}

}