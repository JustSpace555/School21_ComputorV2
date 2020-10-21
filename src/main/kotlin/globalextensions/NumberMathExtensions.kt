package globalextensions

private fun Number.invokeOperation(operation: (Double, Double) -> Double, other: Number): Number =
	operation(this.toDouble(), other.toDouble()).tryCastToInt()

operator fun Number.plus(input: Number): Number = invokeOperation(Double::plus, input)
operator fun Number.times(input: Number): Number = invokeOperation(Double::times, input)
operator fun Number.minus(input: Number): Number = invokeOperation(Double::minus, input)
operator fun Number.div(input: Number): Number = invokeOperation(Double::div, input)
operator fun Number.rem(input: Number): Number = invokeOperation(Double::rem, input)

operator fun Number.unaryMinus(): Number = this * -1

//TODO Проверить
operator fun Number.compareTo(input: Number): Int = (this.toDouble() - input.toDouble()).toInt()

fun Number.tryCastToInt(): Number =
		if (this.toDouble() - this.toInt() == 0.0)
			this.toInt()
		else
			this.toDouble()