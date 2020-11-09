package computorv1.calculations

import computorv1.models.Discriminant
import computorv1.models.PolynomialTerm
import globalextensions.unaryMinus
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import kotlin.math.sqrt

private fun calculateComplexArgs(discriminant: Discriminant): Pair<Complex, Complex> {
	val del = discriminant.argA * 2
	val first = Complex(-discriminant.argB.number, -sqrt(discriminant.result.number.toDouble() * -1)) / del
	val second = Complex(-discriminant.argB.number, sqrt(discriminant.result.number.toDouble() * -1)) / del
	return Pair(first as Complex, second as Complex)
}

private fun calculateTwoArg(discriminant: Discriminant, func: (Double) -> Double): SetNumber =
		((-discriminant.argB + func(sqrt(discriminant.result.number.toDouble()))) / (discriminant.argA * 2))

private fun calculateOneArg(discriminant: Discriminant): SetNumber =
		(if (discriminant.argA < 0.0)
			-discriminant.argC / discriminant.argB
		else
			-discriminant.argB / (discriminant.argA * 2)
		)

internal fun calculateSolutions(polynomial: List<PolynomialTerm>): Triple<Discriminant, Numeric, Numeric?> {
	val discriminant = Discriminant(polynomial)
	return when {
		discriminant.result < 0 -> {
			val complexPair = calculateComplexArgs(discriminant)
			Triple(discriminant, complexPair.first, complexPair.second)
		}

		discriminant.result.compareTo(0.0) == 0 -> {
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