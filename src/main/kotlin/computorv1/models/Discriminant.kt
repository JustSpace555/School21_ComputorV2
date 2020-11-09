package computorv1.models

import models.math.dataset.numeric.SetNumber

internal class Discriminant(polynomial: List<PolynomialTerm>) {
	val argA = (polynomial.find { it.degree == 2 }?.number ?: SetNumber()) as SetNumber
	val argB = (polynomial.find { it.degree == 1 }?.number ?: SetNumber()) as SetNumber
	val argC = (polynomial.find { it.degree == 0 }?.number ?: SetNumber()) as SetNumber

	val result = when {
		(argA).compareTo(0.0) == 0 -> SetNumber()
		else -> argB * argB - SetNumber(4) * argA * argC
	}

	override fun toString(): String = result.toString()
}