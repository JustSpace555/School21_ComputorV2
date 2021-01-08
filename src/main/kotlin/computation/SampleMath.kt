package computation

import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.NonExistDegree
import java.math.MathContext

private const val MAX_OF_ITERATIONS = 20

const val SAMPLE_PI: Double = 3.141592653589793
const val SAMPLE_E: Double = 2.7182818284590452353602874713527
const val SAMPLE_PRECISION: Double = 0.000000001

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

fun sampleFactorial(number: Int): Long = if (number > 1) number * sampleFactorial(number - 1) else 1

fun sampleSin(degree: Double): Double {
	var result = 0.0

	for (i in 0..MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i + 1) / sampleFactorial(2 * i + 1)
	}

	return result.round()
}

fun sampleCos(degree: Double): Double {
	var result = 0.0

	for (i in 0..MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i) / sampleFactorial(2 * i)
	}

	return result.round()
}

fun sampleTan(degree: Double): Double {
	if (sampleAbs(degree) == SAMPLE_PI / 2 || sampleAbs(degree) == 3 * SAMPLE_PI / 2)
		throw NonExistDegree(degree, "tan")

	return sampleSin(degree) / sampleCos(degree)
}
fun sampleCtg(degree: Double): Double {
	if (degree == 0.0 || sampleAbs(degree) == SAMPLE_PI) throw NonExistDegree(degree, "ctg")
	return (sampleCos(degree) / sampleSin(degree)).round()
}

fun sampleArcSin(degree: Double): Double {
	var result = degree

	for (i in 1 .. MAX_OF_ITERATIONS) {
		result += (sampleFactorial(2 * i - 1) * degree.samplePow(2 * i + 1)) /
				(sampleFactorial(2 * i) * (2 * i + 1))
	}

	return result.round()
}

fun sampleArcCos(degree: Double): Double {
	var result = degree

	for (i in 1 .. MAX_OF_ITERATIONS) {
		result += (sampleFactorial(2 * i - 1) * degree.samplePow(2 * i + 1)) /
				(sampleFactorial(2 * i) * (2 * i + 1))
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