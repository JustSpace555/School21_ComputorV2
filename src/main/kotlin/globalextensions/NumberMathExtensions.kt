package globalextensions

import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

private fun Number.castToBigDecimal() = toDouble().toBigDecimal()

private fun Number.invokeOperation(operation: (BigDecimal, BigDecimal) -> BigDecimal, other: Number) =
	operation(this.castToBigDecimal(), other.castToBigDecimal()).tryCastToInt()



operator fun Number.plus(input: Number) = invokeOperation(BigDecimal::add, input)
operator fun Number.times(input: Number) = invokeOperation(BigDecimal::multiply, input)
operator fun Number.minus(input: Number) = invokeOperation(BigDecimal::subtract, input)

operator fun Number.div(input: Number): Number {
	if (input.isZero()) throw DivideByZeroException()
	return this.castToBigDecimal().divide(input.castToBigDecimal(), 9, RoundingMode.HALF_UP).tryCastToInt()
}

operator fun Number.rem(input: Number): Number {
	if (input.isZero()) throw DivideByZeroException()
	return invokeOperation(BigDecimal::remainder, input)
}

operator fun Number.unaryMinus() = this * -1
operator fun Number.compareTo(input: Number) = this.castToBigDecimal().compareTo(input.castToBigDecimal())

fun Number.elevate(other: Number): Number {
	if (other !is Int) throw IllegalOperationException(this::class, other::class, "^")
	return if (other < 0)
		1.0 / this.castToBigDecimal().pow(other * -1, MathContext(9)).toDouble()
	else
		this.castToBigDecimal().pow(other, MathContext(9)).tryCastToInt()
}

fun Number.isZero() = compareTo(0.0) == 0

fun Number.tryCastToInt(): Number =
		if (this.toDouble() - this.toInt() == 0.0) toInt()
		else toDouble()