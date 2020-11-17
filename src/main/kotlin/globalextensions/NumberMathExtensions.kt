package globalextensions

import models.exceptions.computorv2.calcexception.DivideByZeroException
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

private fun Number.castToBigDecimal() = toDouble().toBigDecimal()

private fun Number.invokeOperation(operation: (BigDecimal, BigDecimal) -> BigDecimal, other: Number) =
	operation(this.castToBigDecimal(), other.castToBigDecimal()).tryCastToInt()

private fun Number.isZero() = castToBigDecimal().compareTo(0.0) == 0



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

fun Number.elevate(other: Number): Number = this.toDouble().pow(other.toDouble()).tryCastToInt()

fun Number.tryCastToInt(): Number =
		if (this.toDouble() - this.toInt() == 0.0) toInt()
		else toDouble()