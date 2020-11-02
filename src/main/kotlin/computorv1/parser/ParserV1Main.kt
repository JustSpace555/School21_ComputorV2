package computorv1.parser

import computorv1.models.PolynomialTerm
import computorv1.parser.extensions.findMaxDegree
import computorv1.parser.extensions.putSpaces
import computorv1.parser.extensions.simplifyPolynomial
import computorv1.parser.extensions.toPolynomialList
import models.exceptions.computorv1.calculationexception.EveryNumberIsSolutionException
import models.exceptions.computorv1.calculationexception.NoSolutionsException
import models.exceptions.computorv1.calculationexception.TooHighPolynomialDegree
import models.exceptions.computorv1.parserexception.EqualSignPositionException

internal fun parser(input: String): Pair<List<PolynomialTerm>, Int> {
	val inputArray: List<String> = putSpaces(input).split(' ').filter { it.isNotEmpty() }

	if (inputArray.indexOf("=") != inputArray.lastIndexOf("=")) throw EqualSignPositionException()

	val listPair = inputArray.toPolynomialList()

	var maxDegree = findMaxDegree(listPair).also {
		if (it > 2) throw TooHighPolynomialDegree()
	}

	val simpledPolynomial = simplifyPolynomial(listPair).also {
		if (it[2 - maxDegree].number.toDouble() == 0.0)
			maxDegree = findMaxDegree(it)

		if (it[0].number == 0 && it[1].number == 0) {
			if (it[2].number == 0) throw EveryNumberIsSolutionException(listOf(), 0)
			else throw NoSolutionsException(it, maxDegree)
		}
	}

	return Pair(simpledPolynomial, maxDegree)
}