package calculationtests.variable.wrapping.brackets

import models.dataset.Function
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class BracketsFunctionTest : BracketsExpressionTest() {

	val newFunction = Function("z", "z^2 + z + 1".getList().toTypedArray())

	@Test
	fun validPlusFunctionTest() {
		assertEquals(
				"((x^2) * 2 * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 3.1 * (x^2))",
				(fullBrackets + function).toString()
		)


		assertEquals(
				"((x^2) * 2 * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2) + (z^2 + z + 1))",
				(fullBrackets + newFunction).toString()
		)
	}

	@Test
	fun validMinusFunctionTest() {
		assertEquals(
				"((x^2) * 2 * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 1.1 * (x^2))",
				(fullBrackets - function).toString()
		)


		assertEquals(
				"((x^2) * 2 * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2) - (z^2 + z + 1))",
				(fullBrackets - newFunction).toString()
		)
	}

	@Test
	fun validTimesFunctionTest() {
		assertEquals(
				"((x^2) * 2 * (x^2) * y^3 + (x^2) * (2.4 + 3.3i) + ((x^2) * (x^2)) / (2.2 + 3.3i) + 2.1 * (x^2) * (x^2))",
				(fullBrackets * function).toString()
		)

		assertEquals(
				"((x^2) * 2 * (z^2 + z + 1) * y^3 + (z^2 + z + 1) * (2.4 + 3.3i) + ((x^2) * (z^2 + z + 1)) / (2.2 + 3.3i) + 2.1 * (x^2) * (z^2 + z + 1))",
				(fullBrackets * newFunction).toString()
		)
	}

	@Test
	fun validDivFunctionTest() {
		assertEquals(
				"(((x^2) * 2) / (x^2) * y^3 + (2.4 + 3.3i) / (x^2) + 1 / (2.2 + 3.3i) + (2.1 * (x^2)) / (x^2))",
				(fullBrackets / function).toString()
		)

		assertEquals(
				"(((x^2) * 2) / (z^2 + z + 1) * y^3 + (2.4 + 3.3i) / (z^2 + z + 1) + (x^2) / ((z^2 + z + 1) * (2.2 + 3.3i)) + (2.1 * (x^2)) / (z^2 + z + 1))",
				(fullBrackets / newFunction).toString()
		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Function test`() {
		fullBrackets % function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Function test`() {
		fullBrackets.pow(function)
	}
}