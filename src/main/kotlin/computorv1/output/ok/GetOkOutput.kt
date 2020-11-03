package computorv1.output.ok

import computorv1.models.PolynomialTerm
import globalextensions.compareTo
import globalextensions.times

internal fun getReducedForm(polynomial: List<PolynomialTerm>, degree: Int): String {
	if (polynomial.isEmpty() || polynomial.all { it.number.toDouble() == 0.0 }) return "0"

	val output = StringBuilder()
	polynomial.map {
		if (it.number != 0) {
			if (it.number < 0)
				output.append(" - ${it.number * -1} * X^${it.degree}")
			else
				output.append(" + $it")
		}
	}

	output.delete(0, 3)
	if (polynomial.first().number < 0)
		output.insert(0, "-")

	return output.toString()
}

internal fun getOkOutput(polynomial: List<PolynomialTerm>, degree: Int) =
	"Reduced form: ${getReducedForm(polynomial, degree)} = 0\n" +
	"Polynomial degree: $degree"
