package computorv1.models

import globalextensions.minus
import globalextensions.times

internal class Discriminant(polynomial: List<PolynomialTerm>) {
	val argA = polynomial[0].number
	val argB = polynomial[1].number
	val argC = polynomial[2].number

	val result = when {
		argA.toDouble() == 0.0 -> 0
		else -> argB * argB - 4 * argA * argC
	}

	override fun toString(): String = result.toString()
}