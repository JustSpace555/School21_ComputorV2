package computation

import globalextensions.elevate
import java.math.MathContext

private const val MAX_OF_ITERATIONS = 20

const val SAMPLE_PI: Double = 3.141592653589793
const val SAMPLE_E: Double = 2.7182818284590452353602874713527

fun sampleMax(n1: Double, n2: Double): Double = if (n1 > n2) n1 else n2
fun sampleMin(n1: Double, n2: Double): Double = if (n1 < n2) n1 else n2

fun sampleSqrt(number: Double): Double = number.toBigDecimal().sqrt(MathContext(9)).toDouble()

fun Double.samplePow(degree: Int): Double = this.elevate(degree).toDouble()

fun sampleFactorial(number: Int): Long = if (number > 1) number * sampleFactorial(number - 1) else 1

fun sampleSin(degree: Double): Double {
	var result = 0.0

	for (i in 0..MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i + 1) / sampleFactorial(2 * i + 1)
	}

	return result
}

fun sampleCos(degree: Double): Double {
	var result = 0.0

	for (i in 0..MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i) / sampleFactorial(2 * i)
	}

	return result
}

fun sampleTan(degree: Double) = sampleSin(degree) / sampleCos(degree)
fun sampleCtg(degree: Double) = sampleCos(degree) / sampleSin(degree)

fun sampleArcSin(degree: Double): Double {
	var result = degree

	for (i in 1 .. MAX_OF_ITERATIONS) {
		result += (sampleFactorial(2 * i - 1) * degree.samplePow(2 * i + 1)) /
				(sampleFactorial(2 * i) * (2 * i + 1))
	}

	return result
}

fun sampleArcCos(degree: Double): Double {
	var result = degree

	for (i in 1 .. MAX_OF_ITERATIONS) {
		result += (sampleFactorial(2 * i - 1) * degree.samplePow(2 * i + 1)) /
				(sampleFactorial(2 * i) * (2 * i + 1))
	}

	return SAMPLE_PI / 2 - result
}

fun sampleArcTan(degree: Double): Double {
	var result = 0.0

	for (i in 0 .. MAX_OF_ITERATIONS) {
		val sign = if (i % 2 == 0) 1 else -1
		result += sign * degree.samplePow(2 * i + 1) / (2 * i + 1)
	}

	return result
}

fun sampleArcCtg(degree: Double): Double = SAMPLE_PI / 2 - sampleArcTan(degree)

fun sampleAbs(number: Double): Double = if (number < 0) number * -1 else number

fun sampleExp(number: Double): Double = SAMPLE_E.samplePow(number.toInt())