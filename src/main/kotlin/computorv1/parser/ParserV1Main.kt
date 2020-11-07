package computorv1.parser

import computorv1.models.PolynomialTerm
import computorv1.parser.extensions.putSpaces
import computorv1.parser.extensions.simplifyPolynomial
import computorv1.parser.extensions.toPolynomialList
import models.exceptions.computorv1.calculationexception.EveryNumberIsSolutionException
import models.exceptions.computorv1.calculationexception.NoSolutionsException
import models.exceptions.computorv1.calculationexception.TooHighPolynomialDegreeException
import models.exceptions.computorv1.parserexception.EqualSignAmountException
import models.exceptions.computorv1.parserexception.EqualSignPositionException

internal fun parser(input: String, isNeedToCheckDegree: Boolean): Pair<List<PolynomialTerm>, Int> {
	val inputArray: List<String> = putSpaces(input).split(' ').filter { it.isNotEmpty() }

	val indexOfEqual = inputArray.indexOf("=")
	if (indexOfEqual != inputArray.lastIndexOf("=")) throw EqualSignAmountException()
	else if (indexOfEqual != -1 && indexOfEqual !in 1 until inputArray.lastIndex) throw EqualSignPositionException()

	val listPair = inputArray.toPolynomialList().also { if (it.isEmpty()) throw EveryNumberIsSolutionException(it, 0) }
	var maxDegree: Int

	val simpledPolynomial = simplifyPolynomial(listPair).also {
		if (it.isEmpty()) throw EveryNumberIsSolutionException(listOf(), 0)
		maxDegree = it.first().degree.also { degree ->
			if (isNeedToCheckDegree && degree > 2) throw TooHighPolynomialDegreeException()
		}

		if (it.first().degree == 0 && it.first().number != 0) throw NoSolutionsException(it, maxDegree)
		else if (it.all { term -> term.number == 0 }) throw EveryNumberIsSolutionException(listOf(), 0)
	}

	return Pair(simpledPolynomial, maxDegree)
}