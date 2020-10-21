package models.math.dataset.numeric

import globalextensions.*
import models.exception.calcexception.variable.IllegalOperationException
import models.math.dataset.DataSet
import models.math.dataset.Function
import models.math.dataset.Matrix

data class SetNumber(var number: Number) : Numeric {

	operator fun plus(other: Number) = copy(number = number + other)
	operator fun plus(other: SetNumber) = this + other.number
	override fun plus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')
			is Function -> TODO()
			is Complex -> other.copy(real = other.real + number)
			else -> this + other as SetNumber
		}

	operator fun minus(other: Number) = copy(number = number - other)
	operator fun minus(other: SetNumber) = this - other.number
	override fun minus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')
			is Function -> TODO()
			is Complex -> other.copy(real = SetNumber(number - other.real.number))
			else -> this - other as SetNumber
		}

	operator fun times(other: Number) = copy(number = number * other)
	operator fun times(other: SetNumber) = this * other.number
	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix -> other * this
			is Function -> TODO()
			is Complex -> other.copy(real = other.real * number)
			else -> this * other as SetNumber
		}

	operator fun div(other: Number) = copy(number = number / other)
	operator fun div(other: SetNumber) = this / other.number
	override fun div(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')
			is Function -> TODO()
			is Complex -> throw IllegalOperationException(this::class, Matrix::class, '/')
			else -> this / other as SetNumber
		}

	operator fun rem(other: Number) = copy(number = number % other)
	operator fun rem(other: SetNumber) = this % other.number
	override fun rem(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '%')
			is Function -> TODO()
			is Complex -> throw IllegalOperationException(this::class, Complex::class, '%')
			else -> this % other as SetNumber
		}

	operator fun compareTo(other: Number): Int = (number.toDouble() - other.toDouble()).toInt()
	operator fun compareTo(other: SetNumber): Int = compareTo(other.number)
}