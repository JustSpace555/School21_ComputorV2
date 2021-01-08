package calculationtests.variable.wrapping.fraction

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FractionFunctionTest : FractionExpressionTest() {

	@Test
	fun validPlusFunctionTest() {
		assertEquals("((3.2 + 3.3i) * (x^2)) / (2.2 + 3.3i)", (fraction + function).toString())
	}

	@Test
	fun validMinusFunctionTest() {
		assertEquals("((-1.2 - 3.3i) * (x^2)) / (2.2 + 3.3i)", (fraction - function).toString())
	}

	@Test
	fun validTimesFunctionTest() {
		assertEquals("((x^2) * (x^2)) / (2.2 + 3.3i)", (fraction * function).toString())
	}

	@Test
	fun validDivFunctionTest() {
		assertEquals("1 / (2.2 + 3.3i)", (fraction / function).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Function test`() {
		fraction % function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Function test`() {
		functionStack.pow(function)
	}
}