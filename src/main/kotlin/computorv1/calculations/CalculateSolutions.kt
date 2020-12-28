package computorv1.calculations

import computorv1.models.Discriminant
import computorv1.models.PolynomialTerm
import globalextensions.unaryMinus
import models.dataset.DataSet
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import kotlin.math.sqrt

private fun calculateOneArg(discriminant: Discriminant): SetNumber =
		if (discriminant.argA.isNotZero())
			-discriminant.argB / (discriminant.argA * 2)
		else
			-discriminant.argC / discriminant.argB

private fun calculateTwoArgs(discriminant: Discriminant): Pair<DataSet, DataSet> {
	val del = discriminant.argA * 2
	var isComplex = false
	var dResult = discriminant.result.number.toDouble()

	if (dResult < 0) {
		dResult *= -1
		isComplex = true
	}

	return if (isComplex) {
			Pair(
				Complex(-discriminant.argB, SetNumber(-sqrt(dResult))) / del,
				Complex(-discriminant.argB, SetNumber(sqrt(dResult))) / del
			)
		} else {
			Pair(
				(-discriminant.argB + SetNumber(-sqrt(dResult))) / del,
				(-discriminant.argB + SetNumber(sqrt(dResult))) / del
			)
		}
}

internal fun calculateSolutions(polynomial: List<PolynomialTerm>): Triple<Discriminant, Numeric, Numeric?> {
	val discriminant = Discriminant(polynomial)

	return when {
		discriminant.result.isZero() -> Triple(discriminant, calculateOneArg(discriminant), null)

		else -> {
			val result = calculateTwoArgs(discriminant)
			Triple(discriminant, result.first as Numeric, result.second as Numeric)
		}
	}
}