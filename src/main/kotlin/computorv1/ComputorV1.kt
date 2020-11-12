package computorv1

import computorv1.calculations.calculateSolutions
import computorv1.models.PolynomialTerm
import computorv1.output.getOkOutput
import computorv1.output.getReducedForm
import computorv1.output.getStringSolutions
import computorv1.parser.extensions.simplifyPolynomial
import computorv1.parser.parser

fun computorV1(input: String): String {

	val polynomial = parser(input, true)

	val solutions = calculateSolutions(polynomial.first)

	return getOkOutput(polynomial.first, polynomial.second) + "\n" +
			getStringSolutions(solutions, polynomial.second)
}

fun List<PolynomialTerm>.simplify(): List<PolynomialTerm> = simplifyPolynomial(this)
fun List<PolynomialTerm>.reducedString(parameter: String = ""): String {
	val reducedString = getReducedForm(this)
		.replace("^1", "")
		.replace(" * X^0", "")
		.replace("1 * ", "")
		.replace("X", "x")

	return if (parameter.isNotEmpty()) reducedString.replace("x", parameter) else reducedString
}