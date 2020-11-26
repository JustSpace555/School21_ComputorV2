package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.isNotEmpty
import globalextensions.toPolynomial
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber

internal fun simplifyPolynomial(input: List<PolynomialTerm>): List<PolynomialTerm> {
	val polynomialMap: MutableMap<Int, PolynomialTerm> = mutableMapOf()

	input.forEach {
		val mapPol = polynomialMap[it.degree] ?: PolynomialTerm(SetNumber(0), it.degree, it.name)
		polynomialMap[it.degree] = (mapPol + it).toPolynomial()
	}

	return polynomialMap
		.map { it.value }
		.filter { it.isNotEmpty() }
		.sortedByDescending { it.degree }
}