package computorv1.models

import computorv1.parser.extensions.findPolynomialByDegree
import globalextensions.isEmpty
import models.dataset.numeric.SetNumber

class Discriminant(polynomial: List<PolynomialTerm>) {
	val argA = polynomial.findPolynomialByDegree(2)?.number ?: SetNumber(0)
	val argB = polynomial.findPolynomialByDegree(1)?.number ?: SetNumber(0)
	val argC = polynomial.findPolynomialByDegree(0)?.number ?: SetNumber(0)

	val result = if (argA.isEmpty())
		SetNumber(0)
	else
		argB * argB - SetNumber(4) * argA * argC

	override fun toString(): String = result.toString()
}