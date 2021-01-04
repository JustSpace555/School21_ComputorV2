package calculationtests.variable.wrapping.functionstack

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class FunctionStackFractionTest : FunctionStackExpressionTest() {

	@Test
	fun validPlusFractionTest() {
		assertEquals("((x^2) / (2.2 + 3.3i) + 0.1 * (x^2))", (functionStack + fraction).toString())
	}

	@Test
	fun validMinusFractionTest() {
		assertEquals("(((x^2) * -1) / (2.2 + 3.3i) + 0.1 * (x^2))", (functionStack - fraction).toString())
	}

	@Test
	fun validTimesFractionTest() {
		assertEquals("(x^2) * 0.1 * (x^2) / (2.2 + 3.3i)", (functionStack * fraction).toString())
	}

	@Test
	fun validDivFractionTest() {
		assertEquals("((0.22 + 0.33i) * (x^2)) / (x^2)", (functionStack / fraction).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Fraction test`() {
		functionStack % fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Fraction test`() {
		functionStack.pow(fraction)
	}
}