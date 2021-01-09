package calculationtests.variable.wrapping.functionstack

import models.dataset.wrapping.Brackets
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FunctionStackBracketsTest : FunctionStackExpressionTest() {

	@Test
	fun validPlusBracketsTest() {
		assertEquals("(x^2) * 0.1", (functionStack + Brackets()).toString())
		assertEquals(
				"((x^2) * 2 * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.2 * (x^2))",
				(functionStack + fullBrackets).toString()
		)
	}

	@Test
	fun validMinusBracketsTest() {
		assertEquals("(x^2) * 0.1", (functionStack - Brackets()).toString())
//		assertEquals(
//				"(-2 * (x^2) * y^3 + (-2.4 - 3.3i) + ((x^2) * -1) / (2.2 + 3.3i) - 2 * (x^2))",
//				(functionStack - fullBrackets).toString()
//		)
	}

	@Test
	fun validTimesBracketsTest() {
		assertEquals("0", (functionStack * Brackets()).toString())
		assertEquals("(x^2) * 0.1 * $generalStr", (functionStack * fullBrackets).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Brackets test`() {
		functionStack / Brackets()
	}

	@Test
	fun validDivBracketsTest() {
		assertEquals("((x^2) * 0.1) / $generalStr", (functionStack / fullBrackets).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Brackets test`() {
		functionStack % fullBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Brackets test`() {
		functionStack.pow(fullBrackets)
	}
}