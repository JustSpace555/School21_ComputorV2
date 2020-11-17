package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.toPolynomial
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber

internal fun simplifyPolynomial(input: List<PolynomialTerm>): List<PolynomialTerm> {
	val polynomialMap: MutableMap<Int, PolynomialTerm> = mutableMapOf()

	input.forEach {
		if (!polynomialMap.containsKey(it.degree)) {
			polynomialMap[it.degree] = PolynomialTerm(SetNumber(0), it.degree, it.name)
		}
		polynomialMap[it.degree] = (polynomialMap[it.degree]!! + it).toPolynomial()
	}

	return polynomialMap
		.map { it.value }
		.filter { if (it.number is Numeric) it.number.isNotZero() else true }
		.sortedByDescending { it.degree }
}