package calculationtests.variable.wrapping.fraction

import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class FractionFunctionStackTest : FractionExpressionTest() {

	@Test
	fun validPlusFunctionStackTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction + FunctionStack()).toString())
		assertEquals(
				"((1.22 + 0.33i) * (x^2)) / (2.2 + 3.3i)",
				(fraction + functionStack).toString()
		)
	}

	@Test
	fun validMinusFunctionStackTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction - FunctionStack()).toString())
		assertEquals(
				"((0.78 - 0.33i) * (x^2)) / (2.2 + 3.3i)",
				(fraction - functionStack).toString()
		)
	}

	@Test
	fun validTimesFunctionStackTest() {
		assertEquals("0", (fraction * FunctionStack()).toString())
		assertEquals(
				"((x^2) * 0.1 * (x^2)) / (2.2 + 3.3i)",
				(fraction * functionStack).toString()
		)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div FunctionStack test`() {
		fraction / FunctionStack()
	}

	@Test
	fun validDivFunctionStackTest() {
		assertEquals(
				"(x^2) / ((0.22 + 0.33i) * (x^2))",
				(fraction / functionStack).toString()
		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem FunctionStack test`() {
		fraction % functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow FunctionStack test`() {
		functionStack.pow(functionStack)
	}
}