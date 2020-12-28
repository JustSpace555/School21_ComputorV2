package computorv1

import computorv1.calculations.calculateSolutions
import computorv1.models.PolynomialTerm
import computorv1.output.ok.getReducedForm
import computorv1.output.ok.getOkOutput
import computorv1.output.ok.getSolutions
import computorv1.parser.extensions.simplifyPolynomial
import computorv1.parser.parserComputorV1

fun solver(polynomial: Pair<List<PolynomialTerm>, Int>): String =
	getOkOutput(polynomial.first, polynomial.second) +
	getSolutions(calculateSolutions(polynomial.first), polynomial.second)

fun computorv1(input: String): String = solver(parserComputorV1(input))

fun List<PolynomialTerm>.simplify() = simplifyPolynomial(this)
fun List<PolynomialTerm>.reducedString(parameter: String = "X"): String =
	getReducedForm(this)
		.replace("X", parameter)
		.replace("^1", "")
		.replace(Regex(" \\* \\D?\\^0"), "")
		.replace("1 * ", "")