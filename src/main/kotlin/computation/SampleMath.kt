package computation

import globalextensions.tryCastToInt
import models.exceptions.computorv2.calcexception.FactorialInfinityException
import models.exceptions.computorv2.calcexception.NonExistDegree
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

private const val MAX_OF_ITERATIONS = 20

const val SAMPLE_PI: Double = 3.14159265
const val SAMPLE_E: Double = 2.71828183
const val SAMPLE_PRECISION: Int = 8
const val SAMPLE_FACTORIAL_BORDER: Int = 170

fun sampleMax(n1: Double, n2: Double): Double = if (n1 > n2) n1 else n2
fun sampleMin(n1: Double, n2: Double): Double = if (n1 < n2) n1 else n2

fun sampleSqrt(number: Double): Double {
	var lBorder = 0.0
	var rBorder = sampleMax(1.0, number)
	var mid = (lBorder + rBorder) / 2
	var i = 0
	var p = mid.samplePow(2)

	while (p != number && i < 500) {
		if (lBorder  == rBorder) break

		when {
			p > number -> rBorder = mid
			p < number -> lBorder = mid
			else -> return p
		}
		mid = (lBorder + rBorder) / 2
		p = mid.samplePow(2)
		i++
	}
	return mid
}

fun Double.samplePow(degree: Int): Double =
	when(degree) {
		0 -> 1.0
		in Int.MIN_VALUE .. 0 -> 1 / samplePow(degree * -1)
		1 -> this
		else -> {
			var i = -1
			var output = 1.0
			while (++i < degree) output *= this
			output
		}
	}

fun sampleFactorial(number: Double): Double =
	when {
		number - number.toInt() > 0 -> throw IllegalOperationException(Double::class, Long::class, "fac")
		number > SAMPLE_FACTORIAL_BORDER -> throw FactorialInfinityException(number.toInt())
		else ->  if (number > 1) number * sampleFactorial(number - 1) else 1.0
	}

fun sampleSin(degree: Double): Double {
	val absVal = sampleAbs(degree)
	val delByHalfPi = (absVal / (SAMPLE_PI / 2)).round()
	val delByPi = (absVal / SAMPLE_PI).round()

	when {
		delByPi.tryCastToInt() is Int -> return 0.0
		delByHalfPi.tryCastToInt() is Int -> return if (delByHalfPi.toInt() == 1) 1.0 else -1.0
	}

	var result = 0.0

	for (i in 0..MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i + 1) / sampleFactorial(2.0 * i + 1)
	}

	return result.round()
}

fun sampleCos(degree: Double): Double {
	val absVal = sampleAbs(degree)
	val delByHalfPi = (absVal / (SAMPLE_PI / 2)).round()
	val delByPi = (absVal / SAMPLE_PI).round()

	when {
		delByPi.tryCastToInt() is Int -> return if (delByPi.toInt() % 2 == 0) 1.0 else -1.0
		delByHalfPi.tryCastToInt() is Int && delByHalfPi.toInt() % 2 == 1 -> return 0.0
	}

	var result = 0.0

	for (i in 0..MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i) / sampleFactorial(2.0 * i)
	}

	return result.round()
}

fun sampleTan(degree: Double): Double {
	val cos = sampleCos(degree)
	if (cos == 0.0) throw NonExistDegree(degree, "tan")
	return (sampleSin(degree) / sampleCos(degree)).round()
}

fun sampleCtg(degree: Double): Double {
	val sin = sampleSin(degree)
	if (sin == 0.0) throw NonExistDegree(degree, "ctg")
	return (sampleCos(degree) / sampleSin(degree)).round()
}

fun sampleArcSin(degree: Double): Double {
	var result = degree

	for (i in 1 .. MAX_OF_ITERATIONS) {
		result += (sampleFactorial(2.0 * i - 1) * degree.samplePow(2 * i + 1)) /
				(sampleFactorial(2.0 * i) * (2 * i + 1))
	}

	return result.round()
}

fun sampleArcCos(degree: Double): Double {
	var result = degree

	for (i in 1 .. MAX_OF_ITERATIONS) {
		result += (sampleFactorial(2.0 * i - 1) * degree.samplePow(2 * i + 1)) /
				(sampleFactorial(2.0 * i) * (2 * i + 1))
	}

	return (SAMPLE_PI / 2 - result).round()
}

fun sampleArcTan(degree: Double): Double {
	var result = 0.0

	for (i in 0 .. MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i + 1) / (2 * i + 1)
	}

	return result.round()
}

fun sampleArcCtg(degree: Double): Double = (SAMPLE_PI / 2 - sampleArcTan(degree)).round()

fun sampleAbs(number: Double): Double = if (number < 0) number * -1 else number

fun sampleExp(number: Double): Double = SAMPLE_E.samplePow(number.toInt()).round()

private fun Double.round(): Double =
	String.format("%.${SAMPLE_PRECISION}f", this).replace(",", ".").toDouble()