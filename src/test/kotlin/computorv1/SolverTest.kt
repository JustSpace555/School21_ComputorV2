package computorv1

import models.exceptions.computorv1.EveryNumberIsSolutionException
import models.exceptions.computorv1.NoSolutionException
import models.exceptions.computorv1.PolynomialMaxDegreeException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.parser
import kotlin.reflect.KClass

class SolverTest {

	@Test
	fun testLinearEquationSolver() {
		testInputs(
			arrayOf(arrayOf(
			"5.0 * -2 * -0.5 * X^0 + 3 * X^1 + 3 * X^2 + 3 * X^3 + 1 * X^4 = 1 * X^0 + 0 * X^1 + 3 * X^3 + 1 * X^4 ?",
			"Reduced form: 3 * X^2 + 3 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: -39\nThe two solutions are:\n-0.5 - 1.040833i\n-0.5 + 1.040833i\n"
		)), PolynomialMaxDegreeException::class)

		val inputs: Array<Array<String>> = arrayOf(
//			arrayOf(" = ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("0.0 = 0.0 ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("00 = 0*1 ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("00 = 0*0 ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("1*1 + x = 1 + x ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("5 * X^2 = 5 * X^2 ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("-1 + x + 2*1 = 2 - 1 + x ?", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
		)
		testInputs(inputs, EveryNumberIsSolutionException::class)

		val noSolution = arrayOf(
				arrayOf(
						"1 = 0 ?",
						"Reduced form: 1 * X^0 = 0\nPolynomial degree: 0\nThere is no solution because the equation is inconsistent.\n"
				),
				arrayOf(
						"1*1 + x = -1*1 + x ?",
						"Reduced form: 2 * X^0 = 0\nPolynomial degree: 0\nThere is no solution because the equation is inconsistent.\n"
				)
		)
		testInputs(noSolution, NoSolutionException::class)

		val inputsOk = arrayOf(
			arrayOf("1 + x = 0 ?", "Reduced form: 1 * x^1 + 1 * x^0 = 0\nPolynomial degree: 1\nThe solution is: -1\n"),
			arrayOf("2 * 1 * x ^ 1 = 3 ?", "Reduced form: 2 * x^1 - 3 * x^0 = 0\nPolynomial degree: 1\nThe solution is: 1.5\n"),
			arrayOf("-2 * x ^ 1 = 3 ?", "Reduced form: -2 * x^1 - 3 * x^0 = 0\nPolynomial degree: 1\nThe solution is: -1.5\n"),
			arrayOf("2.0 * x ^ 1 = -3.0 ?", "Reduced form: 2 * x^1 + 3 * x^0 = 0\nPolynomial degree: 1\nThe solution is: -1.5\n"),
			arrayOf(
				"5 = 2*2 + 7 * x ?",
				"Reduced form: -7 * x^1 + 1 * x^0 = 0\nPolynomial degree: 1\nThe solution is: 0.142857143\n"
			)
		)
		testInputs(inputsOk)
	}

	@Test
	fun testQuadraticEquationSolver() {
		val inputs: Array<Array<String>> = arrayOf(
			arrayOf("x ^ 2 + 0 = 0 ?", "Reduced form: 1 * x^2 = 0\nPolynomial degree: 2\nDiscriminant: 0\nThe solution is: 0\n"),
			arrayOf(
				"5 * 10 * 0.1 * X^0 + 13 * 1.0 * X^1 + 3 * X^2 = 1 * X^0 + 1 * X^1 ?",
				"Reduced form: 3 * X^2 + 12 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 96\nThe two solutions are:\n-3.632993162\n-0.367006838\n"
			),
			arrayOf(
				"X^2 - 15 * X + 56 = 0 ?",
				"Reduced form: 1 * X^2 - 15 * X^1 + 56 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 1\nThe two solutions are:\n7\n8\n"
			),
			arrayOf(
				"2 * X^2 + 7 * X + 4 = 0?",
				"Reduced form: 2 * X^2 + 7 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 17\nThe two solutions are:\n-2.780776408\n-0.719223593\n"
			),
			arrayOf(
				"5.0 * -2 * -0.5 * X^0 + 3 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1 ?",
				"Reduced form: 3 * X^2 + 3 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: -39\nThe two solutions are:\n-0.5 - 1.040833i\n-0.5 + 1.040833i\n"
			),
			arrayOf(
				"5.0 * -2 * -0.5 * X^0 + 3 * X^1 + 3 * X^2 + 3 * X^3 + 1 * X^4 = 1 * X^0 + 0 * X^1 + 3 * X^3 + 1 * X^4 ?",
				"Reduced form: 3 * X^2 + 3 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: -39\nThe two solutions are:\n-0.5 - 1.040833i\n-0.5 + 1.040833i\n"
			)
		)
		testInputs(inputs)
	}

	private fun testInputs(inputs: Array<Array<String>>, exceptionKClass: KClass<*>? = null) {
		for (input in inputs) {
			if (input.size != 2) continue

			lateinit var solution: String
			exceptionKClass?.let {
				try {
					solution = parser(input[0])
				} catch (e: Exception) {
					println(e.message)
					assertEquals(exceptionKClass, e::class)
					assertEquals(input[1], e.message)
				}
			} ?: run { solution = parser(input[0]); assertEquals(input[1], solution) }
		}
	}
}
