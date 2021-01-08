package calculationtests.variable.wrapping.functionstack

import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FunctionStackSetNumberTest : FunctionStackExpressionTest() {

	@Test
	fun validPlusSetNumberTest() {
		assertEquals("(x^2) * 0.1", (functionStack + SetNumber()).toString())
		assertEquals("(1 + 0.1 * (x^2))", (functionStack + SetNumber(1)).toString())
		assertEquals("(-1 + 0.1 * (x^2))", (functionStack + SetNumber(-1)).toString())
	}

	@Test
	fun validMinusSetNumberTest() {
		assertEquals("(x^2) * 0.1", (functionStack - SetNumber()).toString())
		assertEquals("(-1 + 0.1 * (x^2))", (functionStack - SetNumber(1)).toString())
		assertEquals("(1 + 0.1 * (x^2))", (functionStack - SetNumber(-1)).toString())
	}

	@Test
	fun validTimesSetNumberTest() {
		assertEquals("0", (functionStack * SetNumber()).toString())
		assertEquals("(x^2) * 0.1", (functionStack * SetNumber(1)).toString())
		assertEquals("-0.1 * (x^2)", (functionStack * SetNumber(-1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber test`() {
		functionStack / SetNumber()
	}

	@Test
	fun validDivSetNumberTest() {
		assertEquals("(x^2) * 0.1", (functionStack / SetNumber(1)).toString())
		assertEquals("-0.1 * (x^2)", (functionStack / SetNumber(-1)).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem SetNumber test`() {
		functionStack % SetNumber(1)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test`() {
		functionStack.pow(SetNumber(1.1))
	}

	@Test
	fun validPowSetNumberTest() {
		assertEquals("0.01 * (x^2) * (x^2)", (functionStack.pow(SetNumber(2)).toString()))
	}
}