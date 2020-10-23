package globalextensions

import models.exception.calcexception.DivideByZeroException
import java.math.BigDecimal

private fun Number.invokeOperation(operation: (BigDecimal, BigDecimal) -> BigDecimal, other: Number) =
	operation(this.toDouble().toBigDecimal(), other.toDouble().toBigDecimal()).tryCastToInt()

operator fun Number.plus(input: Number) = invokeOperation(BigDecimal::add, input)
operator fun Number.times(input: Number) = invokeOperation(BigDecimal::multiply, input)
operator fun Number.minus(input: Number) = invokeOperation(BigDecimal::subtract, input)

operator fun Number.div(input: Number): Number {
	if (input.isZero()) throw DivideByZeroException()
	return invokeOperation(BigDecimal::divide, input)
}

operator fun Number.rem(input: Number): Number {
	if (input.isZero()) throw DivideByZeroException()
	return invokeOperation(BigDecimal::remainder, input)
}

operator fun Number.unaryMinus() = this * -1
operator fun Number.compareTo(input: Number) = this.toDouble().toBigDecimal().compareTo(input.toDouble().toBigDecimal())

fun Number.tryCastToInt(): Number =
		if (this.toDouble() - this.toInt() == 0.0) this.toInt()
		else this.toDouble()

fun Number.isZero() = this.compareTo(0.0) == 0