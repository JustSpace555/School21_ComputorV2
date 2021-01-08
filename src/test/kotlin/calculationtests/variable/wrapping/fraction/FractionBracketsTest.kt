package calculationtests.variable.wrapping.fraction

import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FractionBracketsTest : FractionExpressionTest() {

	@Test
	fun validPlusBracketsTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction + emptyBrackets).toString())
		assertEquals(
				"((x^2) * (2.2 + 3.3i) * y^3 + (0.22 + 0.33i) + (x^2) + (2.2 + 3.3i) * (x^2)) / (2.2 + 3.3i)",
				(fraction + middleBrackets).toString()
		)
	}

	@Test
	fun validMinusBracketsTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction - emptyBrackets).toString())
		assertEquals(
				"((-2.2 - 3.3i) * (x^2) * y^3 + (-0.22 - 0.33i) + (x^2) + (-2.2 - 3.3i) * (x^2)) / (2.2 + 3.3i)",
				(fraction - middleBrackets).toString()
		)
	}

	@Test
	fun validTimesBracketsTest() {
		assertEquals("0", (fraction * emptyBrackets).toString())
		assertEquals(
				"((x^2) * (x^2) * y^3 + (x^2) * 0.1 + (x^2) * (x^2)) / (2.2 + 3.3i)",
				(fraction * middleBrackets).toString()
		)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Brackets test`() {
		fraction / emptyBrackets
	}

	@Test
	fun validDivBracketsTest() {
		assertEquals(
				"(x^2) / ((x^2) * (2.2 + 3.3i) * y^3 + (0.22 + 0.33i) + (2.2 + 3.3i) * (x^2))",
				(fraction / middleBrackets).toString()
		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Brackets test`() {
		fraction % middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Brackets test`() {
		functionStack.pow(middleBrackets)
	}
}