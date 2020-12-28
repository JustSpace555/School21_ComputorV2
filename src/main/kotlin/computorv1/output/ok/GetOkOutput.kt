package computorv1.output.ok

import computorv1.models.PolynomialTerm
import globalextensions.isEmpty

fun getReducedForm(polynomial: List<PolynomialTerm>): String =
	if (polynomial.isEmpty() || polynomial.all { it.number.isEmpty() }) {
		"0"
	} else {
		polynomial.joinToString(" + ").replace(" + -", " - ")
	}

fun getOkOutput(polynomial: List<PolynomialTerm>, degree: Int) =
	"Reduced form: ${getReducedForm(polynomial)} = 0\n" +
	"Polynomial degree: $degree\n"
