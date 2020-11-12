package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import models.math.dataset.DataSet
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber

internal fun simplifyPolynomial(input: List<DataSet>): List<PolynomialTerm> {
	val polynomialMap: MutableMap<Int, PolynomialTerm> = mutableMapOf()

	input.forEach {
		val degree = if (it is PolynomialTerm) it.degree else 0
		if (!polynomialMap.containsKey(degree)) {
			polynomialMap[degree] = PolynomialTerm(SetNumber(0), degree)
		}
		polynomialMap[degree] = (polynomialMap[degree]!! + it) as PolynomialTerm
	}

	return polynomialMap
		.map { it.value }
		.filter { if (it.number is Numeric) it.number.isNotZero() else true }
		.sortedByDescending { SetNumber(it.degree) }
}