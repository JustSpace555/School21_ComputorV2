package calculationtests.variable.wrapping.brackets

import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class BracketsComplexTest : BracketsExpressionTest() {

	@Test
	fun validPlusComplexTest() {
		assertEquals(generalStr, (fullBrackets + Complex(0, 0)).toString())


		assertEquals(
				"((x^2) * 2 * y^3 + (3.5 + 4.4i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
				(fullBrackets + Complex(1.1, 1.1)).toString()
		)

		assertEquals(
				"((x^2) * 2 * y^3 + (3.2 + 4i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
				(fullBrackets + Complex(1.1, 1.1) + Complex(-0.3, -0.4)).toString()
		)
	}

	@Test
	fun validMinusComplexTest() {
		assertEquals(generalStr, (fullBrackets - Complex(0, 0)).toString())


		assertEquals(
				"((x^2) * 2 * y^3 + (2.2 + 3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
				(fullBrackets - Complex(0.2, 0.3)).toString()
		)

		assertEquals(
				"((x^2) * 2 * y^3 + (3.2 + 4i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
				(fullBrackets - Complex(0.2, 0.3) - Complex(-1, -1)).toString()
		)
	}

	@Test
	fun validTimesComplexTest() {
		assertEquals("0", (fullBrackets * SetNumber()).toString())
		assertEquals(generalStr, (fullBrackets * SetNumber(1)).toString())
		assertEquals(
				"(-2 * (x^2) * y^3 + (-2.4 - 3.3i) + ((x^2) * -1) / (2.2 + 3.3i) - 2.1 * (x^2))",
				(fullBrackets * Complex(-1, 0)).toString()
		)


		assertEquals(
				"((4 + 4i) * (x^2) * y^3 + (-1.8 + 11.4i) + ((x^2) * (2 + 2i)) / (2.2 + 3.3i) + (4.2 + 4.2i) * (x^2))",
				(fullBrackets * Complex(2, 2)).toString()
		)

		assertEquals(
				"((0.2 + 0.2i) * (x^2) * y^3 + (-0.09 + 0.57i) + ((x^2) * (0.1 + 0.1i)) / (2.2 + 3.3i) + (0.21 + 0.21i) * (x^2))",
				(fullBrackets * Complex(0.1, 0.1)).toString()
		)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Complex test`() {
		fullBrackets / Complex(0, 0)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Complex test SetNumber div Complex`() {
		assertEquals(
				"(-2 * (x^2) * y^3 + (-2.4 - 3.3i) + (x^2) / (-2.2 - 3.3i) - 2.1 * (x^2))",
				(fullBrackets / Complex(1, 1)).toString()
		)
	}

	@Test
	fun validDivComplexTest() {
		assertEquals(
				"(2 * (x^2) * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
				(fullBrackets / Complex(1, 0)).toString()
		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Complex test`() {
		fullBrackets % Complex(1, 1)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Complex test`() {
		fullBrackets.pow(Complex(1, 1))
	}
}