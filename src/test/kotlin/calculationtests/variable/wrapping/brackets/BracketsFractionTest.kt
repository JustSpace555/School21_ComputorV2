package calculationtests.variable.wrapping.brackets

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class BracketsFractionTest : BracketsExpressionTest() {

	@Test
	fun validPlusFunctionStackTest() {
		assertEquals("((x^2) * y^3 + 0.1 + (x^2) / (2.2 + 3.3i) + (x^2))", (middleBrackets + fraction).toString())
	}

	@Test
	fun validMinusFunctionStackTest() {
		assertEquals("((x^2) * y^3 + 0.1 + ((x^2) * -1) / (2.2 + 3.3i) + (x^2))", (middleBrackets - fraction).toString())
	}

	@Test
	fun validTimesFunctionStackTest() {
		assertEquals(
				"(((x^2) * (x^2)) / (2.2 + 3.3i) * y^3 + ((x^2) * 0.1) / (2.2 + 3.3i) + (x^2) * (x^2) / (2.2 + 3.3i))",
				(middleBrackets * fraction).toString()
		)
	}

	@Test
	fun validDivFunctionStackTest() {
		assertEquals(
				"((x^2) * y^3 + 0.1 + (x^2)) / ((x^2) * 0.1)",
				(middleBrackets / functionStack).toString()
		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Brackets test`() {
		fullBrackets % fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Brackets test`() {
		fullBrackets.pow(fraction)
	}
}