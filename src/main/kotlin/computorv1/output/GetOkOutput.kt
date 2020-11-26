package computorv1.output

import computorv1.models.PolynomialTerm
import globalextensions.isEmpty
import models.dataset.numeric.Numeric

internal fun getReducedForm(polynomial: List<PolynomialTerm>): String =
	if (polynomial.isEmpty() || polynomial.all { it.isEmpty() }) {
		"0"
	} else {
		polynomial.joinToString(" + ").replace(" + -", " - ")
	}

internal fun getOkOutput(polynomial: List<PolynomialTerm>, degree: Int) =
	"Reduced form: ${getReducedForm(polynomial)} = 0\n" +
	"Polynomial degree: $degree"
