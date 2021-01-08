package calculationtests.variable.wrapping.functionstack

import computorv1.models.PolynomialTerm
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FunctionStackPolynomialTermTest : FunctionStackExpressionTest() {

	@Test
	fun validPlusPolynomialTermTest() {
		assertEquals("(0.1 * (x^2) + (x^2) * y^3)", (functionStack + pt).toString())
		assertEquals("0.1 * (x^2)", (functionStack * PolynomialTerm(1, 0)).toString())
	}

	@Test
	fun validMinusPolynomialTermTest() {
		assertEquals("(0.1 * (x^2) + (x^2) * -1 * y^3)", (functionStack - pt).toString())
	}

	//TODO проверить если в полиноме будет FunctionStack и тд
	@Test
	fun validTimesPolynomialTermTest() {
		assertEquals("(x^2) * 0.1 * y^3 * (x^2)", (functionStack * pt).toString())
	}

	@Test
	fun validDivPolynomialTermTest() {
		assertEquals("0.1 / (x^2) * y^-3 * (x^2)", (functionStack / pt).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem PolynomialTerm test`() {
		functionStack % pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow PolynomialTerm test`() {
		functionStack.pow(pt)
	}
}