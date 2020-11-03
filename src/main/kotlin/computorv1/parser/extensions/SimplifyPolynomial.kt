package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import models.math.dataset.numeric.SetNumber

internal fun simplifyPolynomial(input: List<PolynomialTerm>): List<PolynomialTerm> {
	val polynomialMap: MutableMap<Int, PolynomialTerm> = mutableMapOf()

	input.forEach {
		val degree = it.degree
		if (!polynomialMap.containsKey(degree)) {
			polynomialMap[degree] = PolynomialTerm(0, degree)
		}
		polynomialMap[degree] = polynomialMap[degree]!! + it
	}

	return polynomialMap
		.map { it.value }
		.filter { it.number != 0 }
		.sortedByDescending { SetNumber(it.degree) }
}