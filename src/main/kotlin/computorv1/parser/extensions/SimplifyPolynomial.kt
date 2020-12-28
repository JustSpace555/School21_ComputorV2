package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.isNotEmpty

fun simplifyPolynomial(input: List<PolynomialTerm>): List<PolynomialTerm> {
	val polynomialMap: MutableMap<Int, PolynomialTerm> = mutableMapOf()

	input.forEach {
		val mapPol = polynomialMap[it.degree] ?: PolynomialTerm(0, it.degree)
		val res = it + mapPol
		polynomialMap[it.degree] = if (res !is PolynomialTerm) PolynomialTerm(res, 0) else res
	}

	return polynomialMap
		.map { it.value }
		.filter { it.number.isNotEmpty() }
		.sortedByDescending { it.degree }
}