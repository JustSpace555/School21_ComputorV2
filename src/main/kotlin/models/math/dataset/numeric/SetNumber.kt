package models.math.dataset.numeric

class SetNumber(val number: Number): Numeric {

	operator fun SetNumber.plus(input: SetNumber): SetNumber {
		

		if (input.number is Int && this.number is Int)
			return this.copy(this.number + input.number)
		return this.toDouble() + input.toDouble()
	}

	operator fun Number.times(input: Number): Number {
		if (input is Int && this is Int)
			return this * input
		return this.toDouble() * input.toDouble()
	}

	operator fun Number.minus(input: Number): Number {
		if (input is Int && this is Int)
			return this - input
		return this.toDouble() - input.toDouble()
	}

	operator fun Number.div(input: Number): Number {
		if (input is Int && this is Int && this % input == 0)
			return this / input
		return this.toDouble() / input.toDouble()
	}

	operator fun Number.unaryMinus(): Number = this * -1

	operator fun Number.compareTo(input: Number): Int = (this.toDouble() - input.toDouble()).toInt()
}