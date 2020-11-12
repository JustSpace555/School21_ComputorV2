package computorv1.output

import computorv1.models.PolynomialTerm
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber

internal fun getReducedForm(polynomial: List<PolynomialTerm>): String {
	if (polynomial.isEmpty() || polynomial.all { it.number is Numeric && it.number.isZero() }) return "0"

	val output = StringBuilder()
	polynomial.forEach {
		if (it.number is Numeric && it.number.isNotZero()) {
			if (it.number is SetNumber && it.number < 0.0 ||
				it.number is Complex && it.number.real < 0.0
			) {
				output.append(" - ${it.number * SetNumber(-1)} * X^${it.degree}")
			} else {
				output.append(" + $it")
			}
		} else {
			output.append(" + $it")
		}
	}

	output.delete(0, 3)
	if (polynomial.first().number is SetNumber && (polynomial.first().number as SetNumber) < 0.0 ||
		polynomial.first().number is Complex && (polynomial.first().number as Complex).real < 0.0
	) {
		output.insert(0, "-")
	}

	return output.toString()
}

internal fun getOkOutput(polynomial: List<PolynomialTerm>, degree: Int) =
	"Reduced form: ${getReducedForm(polynomial)} = 0\n" +
	"Polynomial degree: $degree"
