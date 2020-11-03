package computorv1.models

import globalextensions.minus
import globalextensions.times

internal class Discriminant(polynomial: List<PolynomialTerm>) {
	val argA = polynomial.find { it.degree == 2 }?.number ?: 0
	val argB = polynomial.find { it.degree == 1 }?.number ?: 0
	val argC = polynomial.find { it.degree == 0 }?.number ?: 0

	val result = when {
		argA.toDouble() == 0.0 -> 0
		else -> argB * argB - 4 * argA * argC
	}

	override fun toString(): String = result.toString()
}