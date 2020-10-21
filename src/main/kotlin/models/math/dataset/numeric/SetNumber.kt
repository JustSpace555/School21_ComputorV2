package models.math.dataset.numeric

import globalextensions.*
import models.exception.calcexception.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix

data class SetNumber(var number: Number) : Numeric {

	operator fun plus(input: Number) = copy(number = number + input)
	override fun plus(input: DataSet): DataSet =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')
			is Function -> TODO()
			is Complex -> input.copy(real = input.real + number)
			else -> plus((input as SetNumber).number)
		}

	operator fun minus(input: Number) = copy(number = number - input)
	override fun minus(input: DataSet): DataSet =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')
			is Function -> TODO()
			is Complex -> input.copy(real = SetNumber(number - input.real.number))
			else -> minus((input as SetNumber).number)
		}

	operator fun times(input: Number) = copy(number = number * input)
	override fun times(input: DataSet): DataSet =
		when (input) {
			is Matrix -> input * this
			is Function -> TODO()
			is Complex -> input.copy(real = SetNumber(input.real.number * number))
			else -> times((input as SetNumber).number)
		}

	operator fun div(input: Number) = copy(number = number / input)
	override fun div(input: DataSet): DataSet =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')
			is Function -> TODO()
			is Complex -> input.copy(real = SetNumber(number / input.real.number))
			else -> div((input as SetNumber).number)
		}

	operator fun rem(input: Number) = copy(number = number % input)
	override fun rem(input: DataSet): DataSet =
		when (input) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '%')
			is Function -> TODO()
			is Complex -> throw IllegalOperationException(this::class, Complex::class, '%')
			else -> rem((input as SetNumber).number)
		}

	operator fun compareTo(input: Number): Int = (number.toDouble() - input.toDouble()).toInt()
	operator fun compareTo(input: SetNumber): Int = compareTo(input.number)
}