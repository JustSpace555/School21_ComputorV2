package computorv1.calculations

import computation.sampleSqrt
import globalextensions.*
import computorv1.models.Discriminant
import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber

private fun calculateComplexArgs(discriminant: Discriminant): Pair<DataSet, DataSet> {
	val discriminantSqrt = (discriminant.result * SetNumber(-1)).sampleSqrt()

	val first = Complex(-discriminant.argB as SetNumber, discriminantSqrt * -1) /
			(SetNumber(2) * discriminant.argA)

	val second = Complex(-discriminant.argB as SetNumber, discriminantSqrt) /
			(SetNumber(2) * discriminant.argA)

	return Pair(first, second)
}

private fun calculateTwoArg(discriminant: Discriminant): Pair<Any, Any> {
	val discriminantSqrt = discriminant.result.sampleSqrt()

	val firstArg = (-discriminant.argB - discriminantSqrt) / (SetNumber(2) * discriminant.argA)
	val secondArg = (-discriminant.argB + discriminantSqrt) / (SetNumber(2) * discriminant.argA)

	return Pair(firstArg, secondArg)
}

private fun calculateOneArg(discriminant: Discriminant): DataSet =
	(if (discriminant.argA.isEmpty())
		-discriminant.argC / discriminant.argB
	else
		-discriminant.argB / (SetNumber(2) * discriminant.argA)
	)

fun calculateSolutions(polynomial: List<PolynomialTerm>): Triple<Discriminant, Any, Any?> {
	val discriminant = Discriminant(polynomial)
	return when {
		(discriminant.result as SetNumber) < 0 -> {
			val complexPair = calculateComplexArgs(discriminant)
			Triple(discriminant, complexPair.first, complexPair.second)
		}
		discriminant.result.compareTo(0) == 0 -> Triple(discriminant, calculateOneArg(discriminant), null)
		else -> {
			val normalPair = calculateTwoArg(discriminant)
			Triple(discriminant, normalPair.first, normalPair.second)
		}
	}
}