package computorv1.calculations

import computorv1.models.Discriminant
import computorv1.models.PolynomialTerm
import globalextensions.unaryMinus
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import kotlin.math.sqrt

private fun calculateComplexArgs(discriminant: Discriminant): Pair<Complex, Complex> {
	val del = discriminant.argA * 2
	val first = Complex(-discriminant.argB.number, -sqrt(discriminant.result.number.toDouble() * -1)) / del
	val second = Complex(-discriminant.argB.number, sqrt(discriminant.result.number.toDouble() * -1)) / del
	return Pair(first as Complex, second as Complex)
}

private fun calculateTwoArg(discriminant: Discriminant, func: (Double) -> Double): SetNumber =
		(-discriminant.argB + func(sqrt(discriminant.result.number.toDouble()))) / (discriminant.argA * 2)

private fun calculateOneArg(discriminant: Discriminant): SetNumber =
		if (discriminant.argA.isNotZero())
			-discriminant.argB / (discriminant.argA * 2)
		else
			-discriminant.argC / discriminant.argB

internal fun calculateSolutions(polynomial: List<PolynomialTerm>): Triple<Discriminant, Numeric, Numeric?> {
	val discriminant = Discriminant(polynomial)
	return when {
		discriminant.result < 0 -> {
			val complexPair = calculateComplexArgs(discriminant)
			Triple(discriminant, complexPair.first, complexPair.second)
		}

		discriminant.result.isZero() -> {
			Triple(discriminant, calculateOneArg(discriminant), null)
		}

		else -> {
			Triple(
				discriminant,
				calculateTwoArg(discriminant, Double::unaryMinus),
				calculateTwoArg(discriminant, Double::unaryPlus)
			)
		}
	}
}