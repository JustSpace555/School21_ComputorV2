package computorv1.parser.extensions

import computorv1.models.PolynomialTerm

internal fun findMaxDegree(input: List<PolynomialTerm>): Int {
	var maxDegree = 0

	for (term in input) {
		if (term.number.toDouble() != 0.0 && term.degree > maxDegree)
			maxDegree = term.degree
	}

	return maxDegree
}