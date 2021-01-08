package calculationtests.variable.wrapping.fraction

import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Fraction
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FractionSetNumberTest : FractionExpressionTest() {
	
	@Test
	fun validPlusSetNumberTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction + SetNumber()).toString())
		assertEquals("((2.2 + 3.3i) + (x^2)) / (2.2 + 3.3i)", (fraction + SetNumber(1)).toString())
		assertEquals("((-2.2 - 3.3i) + (x^2)) / (2.2 + 3.3i)", (fraction + SetNumber(-1)).toString())
	}

	@Test
	fun validMinusSetNumberTest() {
		assertEquals("0", (fraction - fraction).toString())
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction - SetNumber()).toString())
		assertEquals("((-2.2 - 3.3i) + (x^2)) / (2.2 + 3.3i)", (fraction - SetNumber(1)).toString())
		assertEquals("((2.2 + 3.3i) + (x^2)) / (2.2 + 3.3i)", (fraction - SetNumber(-1)).toString())
	}

	@Test
	fun validTimesSetNumberTest() {
		assertEquals("0", (fraction * SetNumber()).toString())
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction * SetNumber(1)).toString())
		assertEquals("((x^2) * -1) / (2.2 + 3.3i)", (fraction * SetNumber(-1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber test`() {
		fraction / SetNumber()
	}

	@Test
	fun validDivSetNumberTest() {
		assertEquals("1", (fraction / Fraction(fraction.denominator, fraction.numerator)).toString())
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction / SetNumber(1)).toString())
		assertEquals("(x^2) / (-2.2 - 3.3i)", (fraction / SetNumber(-1)).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem SetNumber test`() {
		fraction % SetNumber(1)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test`() {
		functionStack.pow(SetNumber(1.1))
	}

	@Test
	fun validPowSetNumberTest() {
		assertEquals("((x^2) * (x^2)) / (-6.05 + 14.52i)", fraction.pow(SetNumber(2)).toString())
	}
}