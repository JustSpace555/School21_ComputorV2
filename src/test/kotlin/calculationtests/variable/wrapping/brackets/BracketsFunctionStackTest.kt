package calculationtests.variable.wrapping.brackets

import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class BracketsFunctionStackTest : BracketsExpressionTest() {

	@Test
	fun validPlusFunctionStackTest() {

		assertEquals(middleStr, (middleBrackets + FunctionStack()).toString())

		assertEquals("((x^2) * y^3 + 0.1 + 1.1 * (x^2))", (middleBrackets + functionStack).toString())

		assertEquals("((x^2) * y^3 + 0.1 + 1.1 * (x^2))", (functionStack + middleBrackets).toString())
	}

	@Test
	fun validMinusFunctionStackTest() {

		assertEquals(middleStr, (middleBrackets - FunctionStack()).toString())

		assertEquals("((x^2) * y^3 + 0.1 + 0.9 * (x^2))", (middleBrackets - functionStack).toString())
	}

	@Test
	fun validTimesFunctionStackTest() {

		assertEquals("0", (middleBrackets * FunctionStack()).toString())

		assertEquals(
				"((x^2) * 0.1 * y^3 * (x^2) + 0.01 * (x^2) + 0.1 * (x^2) * (x^2))",
				(middleBrackets * functionStack).toString()
		)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div FunctionStack test`() {
		fullBrackets / FunctionStack()
	}

	@Test
	fun validDivFunctionStackTest() {
		assertEquals("$middleStr / ($functionStack)", (middleBrackets / functionStack).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Brackets test`() {
		fullBrackets % functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Brackets test`() {
		fullBrackets.pow(functionStack)
	}
}