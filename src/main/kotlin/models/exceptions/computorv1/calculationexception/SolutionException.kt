package models.exceptions.computorv1.calculationexception

import computorv1.models.PolynomialTerm
import computorv1.output.getOkOutput
import models.exceptions.computorv1.ComputorV1Exception
import models.exceptions.globalexceptions.CalculationException

@ComputorV1Exception
open class SolutionException(polynomialList: List<PolynomialTerm>, degree: Int) : CalculationException() {
	override val message: String = getOkOutput(polynomialList, degree) + "\n"
}

@ComputorV1Exception
class EveryNumberIsSolutionException(
	polynomialList: List<PolynomialTerm>,
	degree: Int
) : SolutionException(polynomialList, degree) {
	override val message: String = super.message + "Every value for X is a solution."
}

@ComputorV1Exception
class NoSolutionsException(
	polynomialList: List<PolynomialTerm>,
	degree: Int
) : SolutionException(polynomialList, degree) {
	override val message: String = super.message + "There is no solution because the equation is inconsistent."
}