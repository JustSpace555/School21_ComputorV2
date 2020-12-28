package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.isNotEmpty

fun findMaxDegree(input: List<PolynomialTerm>): Int {
	var maxDegree = 0

	for (term in input) {
		if (term.number.isNotEmpty() && term.degree > maxDegree)
			maxDegree = term.degree
	}

	return maxDegree
}

fun List<PolynomialTerm>.findPolynomialByDegree(degree: Int): PolynomialTerm? {
	var polynomial: PolynomialTerm? = null
	this.forEach(fun(p: PolynomialTerm) {
		if (p.degree == degree)
			polynomial = p
	})
	return polynomial
}