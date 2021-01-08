package calculationtests.variable.wrapping.fraction

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FractionPolynomialTermTest : FractionExpressionTest() {

	@Test
	fun validPlusPolynomialTermTest() {
		assertEquals("((x^2) + (x^2) * (2.2 + 3.3i) * y^3) / (2.2 + 3.3i)", (fraction + pt).toString())
	}

	@Test
	fun validMinusPolynomialTermTest() {
		assertEquals("((x^2) + (-2.2 - 3.3i) * (x^2) * y^3) / (2.2 + 3.3i)", (fraction - pt).toString())
	}

	@Test
	fun validTimesPolynomialTermTest() {
		assertEquals("((x^2) * (x^2)) / (2.2 + 3.3i) * y^3", (fraction * pt).toString())
	}

	@Test
	fun validDivPolynomialTermTest() {
		assertEquals("1 / (2.2 + 3.3i) * y^3", (fraction / pt).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem PolynomialTerm test`() {
		fraction % pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow PolynomialTerm test`() {
		functionStack.pow(pt)
	}
}