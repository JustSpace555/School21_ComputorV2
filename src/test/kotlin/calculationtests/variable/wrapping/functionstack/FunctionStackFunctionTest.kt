package calculationtests.variable.wrapping.functionstack

import models.dataset.Function
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class FunctionStackFunctionTest : FunctionStackExpressionTest() {

	private val secondFunction = Function("z", "z".getList().toTypedArray())

	@Test
	fun validPlusFunctionTest() {
		assertEquals("1.1 * (x^2)", (functionStack + function).toString())
		assertEquals("(0.1 * (x^2) + (z))", (functionStack + secondFunction).toString())
	}

	@Test
	fun validMinusFunctionTest() {
		assertEquals("-0.9 * (x^2)", (functionStack - function).toString())
		assertEquals("(0.1 * (x^2) - (z))", (functionStack - secondFunction).toString())
	}

	@Test
	fun validTimesFunctionTest() {
		assertEquals("(x^2) * 0.1 * (x^2)", (functionStack * function).toString())
		assertEquals("(x^2) * 0.1 * (z)", (functionStack * secondFunction).toString())
	}

	@Test
	fun validDivFunctionTest() {
		assertEquals("((x^2) * 0.1) / (x^2)", (functionStack / function).toString())
		assertEquals("((x^2) * 0.1) / (z)", (functionStack / secondFunction).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Function test`() {
		functionStack % function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Function test`() {
		functionStack.pow(function)
	}
}