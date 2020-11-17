package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.toPolynomial
import models.dataset.numeric.SetNumber

internal fun List<String>.toPolynomialList(): List<PolynomialTerm> {
	val output = mutableListOf<PolynomialTerm>()
	var isWasEquality = false

	for (element in this) {
		if (element == "=") {
			isWasEquality = true
			continue
		}

		var term = element.toPolynomialTerm()
		if (isWasEquality)
			term = (term * SetNumber(-1)).toPolynomial()

		output.add(term)
	}
	return output.toList()
}