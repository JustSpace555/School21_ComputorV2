package calculationtests.variable.wrapping.fraction

import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Fraction
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class FractionFractionTest : FractionExpressionTest() {

	@Test
	fun validPlusFractionTest() {
		assertEquals("((x^2) * 2) / (2.2 + 3.3i)", (fraction + fraction).toString())
		assertEquals(
				"((2.2 + 3.3i) + 2 * (x^2)) / (4.4 + 6.6i)",
				(fraction + Fraction(SetNumber(1), SetNumber(2))).toString()
		)
	}

	@Test
	fun validMinusFractionTest() {
		assertEquals("0", (fraction - fraction).toString())
		assertEquals(
				"((-2.2 - 3.3i) + 2 * (x^2)) / (4.4 + 6.6i)",
				(fraction - Fraction(SetNumber(1), SetNumber(2))).toString()
		)
	}

	@Test
	fun validTimesFractionTest() {
		assertEquals("((x^2) * (x^2)) / (-6.05 + 14.52i)", (fraction * fraction).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Fraction test`() {
		fraction / Fraction(SetNumber(0), function)
	}

	@Test
	fun validDivFractionTest() {
		assertEquals("1", (fraction / fraction).toString())
		assertEquals(
				"((x^2) * 2) / (2.2 + 3.3i)",
				(fraction / Fraction(SetNumber(1), SetNumber(2))).toString()
		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Fraction test`() {
		fraction % fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Fraction test`() {
		functionStack.pow(fraction)
	}
}