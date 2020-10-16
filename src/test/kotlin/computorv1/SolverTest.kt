import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import output.error.printErrorOutput
import parser.SignalCodes
import parser.parser
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class SolverTest {

	private val out = ByteArrayOutputStream()
	private val originalOut = System.out

	@Before
	fun setUpStream() {
		System.setOut(PrintStream(out))
	}

	@After
	fun restoreStream() {
		System.setOut(originalOut)
	}

	@Test
	fun testLinearEquationSolver() {
		val inputs: Array<Array<String>> = arrayOf(
			arrayOf("", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("0.0 = 0.0", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("00 = 0*1", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("00 = 0*0", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("1*1 + x = 1 + x", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("5 * X^2 = 5 * X^2", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
			arrayOf("-1 + x + 2*1 = 2 - 1 + x", "Reduced form: 0 = 0\nPolynomial degree: 0\nEvery value for X is a solution.\n"),
		)
		testInputs(inputs, SignalCodes.EVERY_NUMBER_IS_SOLUTION)

		val noSolution = arrayOf(
				arrayOf(
						"1",
						"Reduced form: 1 * X^0 = 0\nPolynomial degree: 0\nThere is no solution because the equation is inconsistent.\n"
				),
				arrayOf(
						"1*1 + x = -1*1 + x",
						"Reduced form: 2 * X^0 = 0\nPolynomial degree: 0\nThere is no solution because the equation is inconsistent.\n"
				)
		)
		testInputs(noSolution, SignalCodes.NO_SOLUTION)

		val inputsOk = arrayOf(
			arrayOf("1 + x", "Reduced form: 1 * X^1 + 1 * X^0 = 0\nPolynomial degree: 1\nThe solution is: -1\n"),
			arrayOf("2 * 1 * x ^ 1 = 3", "Reduced form: 2 * X^1 - 3 * X^0 = 0\nPolynomial degree: 1\nThe solution is: 1.5\n"),
			arrayOf("-2 * x ^ 1 = 3", "Reduced form: -2 * X^1 - 3 * X^0 = 0\nPolynomial degree: 1\nThe solution is: -1.5\n"),
			arrayOf("2.0 * x ^ 1 = -3.0", "Reduced form: 2 * X^1 + 3 * X^0 = 0\nPolynomial degree: 1\nThe solution is: -1.5\n"),
			arrayOf(
				"5 = 2*2 + 7 * x",
				"Reduced form: -7 * X^1 + 1 * X^0 = 0\nPolynomial degree: 1\nThe solution is: 0.14285714285714285\n"
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
				"Reduced form: 3 * X^2 + 12 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 96\nThe two solutions are:\n-3.632993161855452\n-0.3670068381445481\n"
			),
			arrayOf(
				"x^2 - 15 * X + 56 = 0",
				"Reduced form: 1 * X^2 - 15 * X^1 + 56 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 1\nThe two solutions are:\n7\n8\n"
			),
			arrayOf(
				"+2 * X^2 + 7 * X + 4",
				"Reduced form: 2 * X^2 + 7 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: 17\nThe two solutions are:\n-2.7807764064044154\n-0.7192235935955849\n"
			),
			arrayOf(
				"5.0 * -2 * -0.5 * X^0 + 3 * X^1 + 3 * X^2 = 1 * X^0 + 0 * X^1",
				"Reduced form: 3 * X^2 + 3 * X^1 + 4 * X^0 = 0\nPolynomial degree: 2\nDiscriminant: -39\nThe two solutions are:\n-0.5 - 1.0408329997330663i\n-0.5 + 1.0408329997330663i\n"
			)
		)
		testInputs(inputs)
	}

	private fun testInputs(inputs: Array<Array<String>>, code: SignalCodes = SignalCodes.OK) {
		for (input in inputs) {
			if (input.size != 2) continue
			out.reset()

			val polynomial = parser(input[0])
			if (polynomial.third != code)
				throw Exception("Test fail on: ${input[0]} with ${polynomial.third}\n")

			if (polynomial.third == SignalCodes.OK)
				solver(polynomial)
			else
				printErrorOutput(polynomial.first, polynomial.second, polynomial.third)

			assertEquals(input[1], out.toString())
		}
	}
}
