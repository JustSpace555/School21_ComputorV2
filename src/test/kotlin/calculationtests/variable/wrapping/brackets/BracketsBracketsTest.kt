package calculationtests.variable.wrapping.brackets

import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class BracketsBracketsTest : BracketsExpressionTest() {

	@Test
	fun validPlusBracketsTest() {
		assertEquals(
				"((x^2) * 2 * y^3 + 0.2 + 2 * (x^2))",
				(middleBrackets + middleBrackets).toString()
		)

		assertEquals(
				"((x^2) * 2 * y^3 + (x^2) * y^3 + (2.5 + 3.3i) + (x^2) / (2.2 + 3.3i) + 3.1 * (x^2))",
				(fullBrackets + middleBrackets).toString()
		)

		assertEquals(
				"((x^2) * y^3 + (x^2) * 2 * y^3 + (2.5 + 3.3i) + (x^2) / (2.2 + 3.3i) + 3.1 * (x^2))",
				(middleBrackets + fullBrackets).toString()
		)
	}

	@Test
	fun validMinusBracketsTest() {
		assertEquals("((x^2) * y^3 + (x^2) * -1 * y^3)", (middleBrackets - middleBrackets).toString())

		assertEquals(
				"((x^2) * 2 * y^3 + (x^2) * -1 * y^3 + (2.3 + 3.3i) + (x^2) / (2.2 + 3.3i) + 1.1 * (x^2))",
				(fullBrackets - middleBrackets).toString()
		)

		assertEquals(
				"((x^2) * y^3 - 2 * (x^2) * y^3 + (-2.3 - 3.3i) + ((x^2) * -1) / (2.2 + 3.3i) - 1.1 * (x^2))",
				(middleBrackets - fullBrackets).toString()
		)
	}

	@Test
	fun validTimesBracketsTest() {
		assertEquals(
				"((x^2) * (x^2) * y^6 + 0.2 * (x^2) * y^3 + 0.01 + (x^2) * 2 * y^3 * (x^2) + 0.2 * (x^2) + (x^2) * (x^2))",
				(middleBrackets * middleBrackets).toString()
		)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Brackets test`() {
		fullBrackets / emptyBrackets
	}

	@Test
	fun validDivBracketsTest() {
		assertEquals("$middleStr / $generalStr", (middleBrackets / fullBrackets).toString())
		assertEquals("$generalStr / $middleStr", (fullBrackets / middleBrackets).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Brackets test`() {
		fullBrackets % middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Brackets test`() {
		fullBrackets.pow(middleBrackets)
	}
}