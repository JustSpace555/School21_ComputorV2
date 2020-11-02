package computorv1.calculations

import computorv1.models.Discriminant
import computorv1.models.PolynomialTerm
import globalextensions.*
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import kotlin.math.sqrt

private fun calculateComplexArgs(discriminant: Discriminant): Pair<Complex, Complex> {
	val del = SetNumber(2 * discriminant.argA)
	val first = Complex(-discriminant.argB, -sqrt(discriminant.result.toDouble() * -1)) / del
	val second = Complex(-discriminant.argB, sqrt(discriminant.result.toDouble() * -1)) / del
	return Pair(first as Complex, second as Complex)
}

private fun calculateTwoArg(discriminant: Discriminant, func: (Double) -> Double): Number =
		((-discriminant.argB + func(sqrt(discriminant.result.toDouble()))) / (2 * discriminant.argA)).tryCastToInt()

private fun calculateOneArg(discriminant: Discriminant): Number =
		(if (discriminant.argA.toDouble() == 0.0)
			-discriminant.argC.toDouble() / discriminant.argB
		else
			-discriminant.argB / (2 * discriminant.argA)
		).tryCastToInt()

internal fun calculateSolutions(polynomial: List<PolynomialTerm>): Triple<Discriminant, Numeric, Numeric?> {
	val discriminant = Discriminant(polynomial)
	return when {
		discriminant.result < 0 -> {
			val complexPair = calculateComplexArgs(discriminant)
			Triple(discriminant, complexPair.first, complexPair.second)
		}
		discriminant.result.toDouble() == 0.0 -> {
			Triple(discriminant, SetNumber(calculateOneArg(discriminant)), null)
		}
		else -> {
			Triple(
				discriminant,
				SetNumber(calculateTwoArg(discriminant, Double::unaryMinus)),
				SetNumber(calculateTwoArg(discriminant, Double::unaryPlus))
			)
		}
	}
}