package computorv1.parser

import models.exceptions.computorv1.EqualSignAmountException
import models.exceptions.computorv1.EveryNumberIsSolutionException
import models.exceptions.computorv1.NoSolutionException
import models.exceptions.computorv1.PolynomialMaxDegreeException
import computorv1.models.PolynomialTerm
import computorv1.parser.extensions.*
import kotlin.text.split

fun parserComputorV1(input: String): Pair<List<PolynomialTerm>, Int> {
	val inputArray: List<String> = putSpacesComputorV1(input)
		.split(' ')
		.filter { it.isNotEmpty() && it.isNotBlank() }

	if (inputArray.lastIndexOf("=") != inputArray.indexOf("="))
		throw EqualSignAmountException()

	var maxDegree: Int
	val simpledPolynomial = simplifyPolynomial(toPolynomialList(inputArray)).also {

		 maxDegree = findMaxDegree(it).also { degree ->
			if (degree > 2) throw PolynomialMaxDegreeException()
		 }

		if (it.findPolynomialByDegree(2) == null && it.findPolynomialByDegree(1) == null) {
			if (it.findPolynomialByDegree(0) == null)
				throw EveryNumberIsSolutionException(it, maxDegree)
			else
				throw NoSolutionException(it, maxDegree)
		}
	}

	return Pair(simpledPolynomial, maxDegree)
}