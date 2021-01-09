package calculationtests.variable.wrapping.functionstack

import models.dataset.Function
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FunctionStackFunctionStackTest : FunctionStackExpressionTest() {

	private val newFunctionStack = FunctionStack(SetNumber(2), Function("z", arrayOf("z")))

	@Test
	fun validPlusFunctionStackTest() {
		assertEquals("0.2 * (x^2)", (functionStack + functionStack).toString())
		assertEquals("(0.1 * (x^2) + 2 * (z))", (functionStack + newFunctionStack).toString())
	}

	@Test
	fun validMinusFunctionStackTest() {
		assertEquals("0", (functionStack - functionStack).toString())
		assertEquals("(0.1 * (x^2) - 2 * (z))", (functionStack - newFunctionStack).toString())
//		assertEquals("((x^2) * (x^2) * -1 * y^3 - (x^2) * (x^2))", (functionStack - function * middleBrackets).toString())
	}

	@Test
	fun validTimesFunctionStackTest() {
		assertEquals("0.01 * (x^2) * (x^2)", (functionStack * functionStack).toString())
		assertEquals("0.2 * (x^2) * (z)", (functionStack * newFunctionStack).toString())
	}

	@Test
	fun validDivFunctionStackTest() {
		assertEquals("1", (functionStack / functionStack).toString())
		assertEquals("((x^2) * 0.1) / (2 * (z))", (functionStack / newFunctionStack).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem FunctionStack test`() {
		functionStack % functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow FunctionStack test`() {
		functionStack.pow(functionStack)
	}
}