package computorv1

import computorv1.calculations.calculateSolutions
import computorv1.output.ok.getOkOutput
import computorv1.output.ok.getReducedForm
import computorv1.output.ok.getStringSolutions
import computorv1.parser.parser

fun computorV1(input: String): String {

	val polynomial = parser(input)

	val solutions = calculateSolutions(polynomial.first)

	return getOkOutput(polynomial.first, polynomial.second) + "\n" +
			getStringSolutions(solutions, polynomial.second)
}

fun List<String>.getSimplifiedFunctionString(parameter: String): String {
	val parsed = parser(this.joinToString(""))
	return getReducedForm(parsed.first, parsed.second).replace("X", parameter)
}
