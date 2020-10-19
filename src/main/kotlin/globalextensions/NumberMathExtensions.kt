package globalextensions

operator fun Number.plus(input: Number): Number = (this.toDouble() + input.toDouble()).tryCastToInt()
operator fun Number.times(input: Number): Number = (this.toDouble() * input.toDouble()).tryCastToInt()
operator fun Number.minus(input: Number): Number = (this.toDouble() - input.toDouble()).tryCastToInt()
operator fun Number.div(input: Number): Number = (this.toDouble() / input.toDouble()).tryCastToInt()
operator fun Number.rem(input: Number): Number = (this.toDouble() % input.toDouble()).tryCastToInt()

operator fun Number.unaryMinus(): Number = this * -1

operator fun Number.compareTo(input: Number): Int = (this.toDouble() - input.toDouble()).toInt()

fun Number.tryCastToInt(): Number =
		if (this.toDouble() - this.toInt() == 0.0)
			this.toInt()
		else
			this.toDouble()