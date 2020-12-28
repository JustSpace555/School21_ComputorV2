package models.exceptions.computorv1

import computorv1.models.PolynomialTerm
import models.exceptions.ComputorException
import computorv1.output.ok.getOkOutput

@ComputorV1Exception
abstract class SolutionException : ComputorException()

@ComputorV1Exception
class EveryNumberIsSolutionException(
	polynomial: List<PolynomialTerm>,
	degree: Int
) : SolutionException() {
	override val message: String = getOkOutput(polynomial, degree) + "Every value for X is a solution.\n"
}

@ComputorV1Exception
class NoSolutionException(
	polynomial: List<PolynomialTerm>,
	degree: Int
) : SolutionException() {
	override val message: String = getOkOutput(polynomial, degree) +
			"There is no solution because the equation is inconsistent.\n"
}