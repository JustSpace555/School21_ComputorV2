package computorv1

import computorv1.calculations.calculateSolutions
import computorv1.models.PolynomialTerm
import computorv1.output.getOkOutput
import computorv1.output.getReducedForm
import computorv1.output.getStringSolutions
import computorv1.parser.extensions.simplifyPolynomial
import computorv1.parser.parser
import globalextensions.mapToPolynomialList

fun computorV1(input: String): String {

	val polynomial = parser(input, true)

	val solutions = calculateSolutions(polynomial.first)

	return getOkOutput(polynomial.first, polynomial.second) + "\n" +
			getStringSolutions(solutions, polynomial.second) + "\n"
}

fun List<PolynomialTerm>.simplify(): List<PolynomialTerm> = simplifyPolynomial(this.mapToPolynomialList())
fun List<PolynomialTerm>.reducedString(parameter: String = "X"): String =
	getReducedForm(this)
		.replace("X", parameter)
		.replace("^1", "")
		.replace(Regex(" \\* [a-zA-Z]?\\^0"), "")
		.replace("1 * ", "")