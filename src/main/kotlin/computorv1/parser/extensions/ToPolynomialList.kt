package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.times

internal fun List<String>.toPolynomialList(): List<PolynomialTerm> {
	val output = mutableListOf<PolynomialTerm>()
	var isWasEquality = false

	for (element in this) {
		if (element == "=") {
			isWasEquality = true
			continue
		}

		val term = element.toPolynomialTerm()
		if (isWasEquality)
			term.number *= -1

		output.add(term)
	}
	return output.toList()
}