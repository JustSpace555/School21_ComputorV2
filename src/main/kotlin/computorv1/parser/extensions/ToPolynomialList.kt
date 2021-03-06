package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import models.dataset.numeric.SetNumber

fun toPolynomialList(input: List<String>): List<PolynomialTerm> {
	val output = mutableListOf<PolynomialTerm>()
	var isWasEquality = false

	for (element in input) {
		if (element == "=") {
			isWasEquality = true
			continue
		}

		var term = toPolynomialTerm(element)
		if (isWasEquality)
			term = PolynomialTerm(term.number * SetNumber(-1), term.degree, term.name)

		output.add(term)
	}
	return output.toList()
}