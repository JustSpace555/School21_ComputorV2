package computorv1

import ComputorTest
import models.exceptions.ComputorException
import models.exceptions.computorv1.calculationexception.EveryNumberIsSolutionException
import models.exceptions.computorv1.calculationexception.NoSolutionsException
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.reflect.KClass

class SolverTest : ComputorTest() {

	@Test
	fun testLinearEquationSolver() {
		val inputs: Array<Array<String>> = arrayOf(
			arrayOf("", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
			arrayOf("0.0 = 0.0", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
			arrayOf("00 = 0*1", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
			arrayOf("00 = 0*0", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
			arrayOf("1*1 + x = 1 + x", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
			arrayOf("5 * X^2 = 5 * X^2", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
			arrayOf("-1 + x + 2*1 = 2 - 1 + x", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution."),
		)
		testInputs(inputs, EveryNumberIsSolutionException::class)

		val noSolution = arrayOf(
				arrayOf(
						"1",
						"Reduced form: 1 * X^0 = 0\nPolynomial degree: 0\nThere is no solution because the equation is inconsistent."
				),
				arrayOf(
						"1*1 + x = -1*1 + x",
						"Reduced form: 2 * X^0 = 0\nPolynomial degree: 0\nThere is no solution because the equation is inconsistent."
				)
		)
		testInputs(noSolution, NoSolutionsException::class)

		val inputsOk = arrayOf(
			arrayOf("1 + x", "Reduced form: 1 * X^1 + 1 * X^0 = 0\nPolynomial degree: 1\nThe solution is: -1\n"),
			arrayOf("2 * 1 * x ^ 1 = 3", "Reduced form: 2 * X^1 - 3 * X^0 = 0\nPolynomial degree: 1\nThe solution is: 1.5\n"),
			arrayOf("-2 * x ^ 1 = 3", "Reduced form: -2 * X^1 - 3 * X^0 = 0\nPolynomial degree: 1\nThe solution is: -1.5\n"),
			arrayOf("2.0 * x ^ 1 = -3.0", "Reduced form: 2 * X^1 + 3 * X^0 = 0\nPolynomial degree: 1\nThe solution is: -1.5\n"),
			arrayOf(
				"5 = 2*2 + 7 * x",
				"Reduced form: -7 * X^1 + 1 * X^0 = 0\nPolynomial degree: 1\nThe solution is: 0.142857143\n"
			)
		)
		testInputs(inputsOk)
	}

	@Test
	fun testQuadraticEquationSolver() {
		val inputs: Array<Array<String>> = arrayOf(
			arrayOf("x ^ 2 + 0 = 0", "Reduced form: 1 * X^2 = 0\nPolynomial degree: 2\nDiscriminant: 0\nThe solution is: 0\n"),
			arrayOf(
				"5 * 10 * 0.1 * X^0 + 13 * 1.0 * X^1 + 3 * X^2 = 1 * X^0 + 1 * X^1",
				"Reduced form: 3 * X^2 + 12 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 96\nThe two solutions are:\n-3.632993162\n-0.367006838\n"
			),
			arrayOf(
				"x^2 - 15 * X + 56 = 0",
				"Reduced form: 1 * X^2 - 15 * X^1 + 56 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 1\nThe two solutions are:\n7\n8\n"
			),
			arrayOf(
				"+2 * X^2 + 7 * X + 4",
				"Reduced form: 2 * X^2 + 7 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 17\nThe two solutions are:\n-2.780776406\n-0.719223594\n"
			),
			arrayOf(
				"5.0 * -2 * -0.5 * X^0 + 3 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1",
				"Reduced form: 3 * X^2 + 3 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: -39\nThe two solutions are:\n-0.5 - 1.040833i\n-0.5 + 1.040833i\n"
			)
		)
		testInputs(inputs)
	}

	private fun testInputs(inputs: Array<Array<String>>, exception: KClass<*>? = null) {
		for (input in inputs) {
			if (input.size != 2) continue

			exception?.let {
				try {
					computorV1(input[0])
				} catch (e: ComputorException) {
					assertEquals(exception, e::class)
				}
			} ?: assertEquals(input[1], computorV1(input[0]))
		}
	}
}
