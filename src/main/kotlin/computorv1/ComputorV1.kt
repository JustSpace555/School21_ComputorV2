package computorv1

import computorv1.calculations.calculateSolutions
import computorv1.models.PolynomialTerm
import computorv1.output.getOkOutput
import computorv1.output.getReducedForm
import computorv1.output.getStringSolutions
import computorv1.parser.parser

fun computorV1(input: String): String {

	val polynomial = parser(input, true)

	val solutions = calculateSolutions(polynomial.first)

	return getOkOutput(polynomial.first, polynomial.second) + "\n" +
			getStringSolutions(solutions, polynomial.second)
}

fun List<String>.getSimplifiedFunction(parameter: String): List<PolynomialTerm> = parser(
	this.apply { toMutableList().replaceAll { if (it == parameter) "x" else it } }.joinToString(""),
	false
).first

fun List<String>.getSimplifiedFunctionString(parameter: String): String =
	getReducedForm(this.getSimplifiedFunction(parameter)).replace("X", parameter)

fun List<PolynomialTerm>.getReducedListTermForm() = getReducedForm(this)
