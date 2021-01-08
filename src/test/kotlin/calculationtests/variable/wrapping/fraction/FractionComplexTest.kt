package calculationtests.variable.wrapping.fraction

import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FractionComplexTest : FractionExpressionTest() {

	@Test
	fun validPlusComplexTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction + Complex(0, 0)).toString())
		assertEquals("((-6.05 + 14.52i) + (x^2)) / (2.2 + 3.3i)", (fraction + complex).toString())
	}

	@Test
	fun validMinusComplexTest() {
		assertEquals("(x^2) / (2.2 + 3.3i)", (fraction - Complex(0, 0)).toString())
		assertEquals("((6.05 - 14.52i) + (x^2)) / (2.2 + 3.3i)", (fraction - complex).toString())
	}

	@Test
	fun validTimesComplexTest() {
		assertEquals("0", (fraction * Complex(0, 0)).toString())
		assertEquals("x^2", (fraction * complex).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Complex test`() {
		fraction / Complex(0, 0)
	}

	@Test
	fun validDivComplexTest() {
		assertEquals("(x^2) / (-6.05 + 14.52i)", (fraction / complex).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Complex test`() {
		fraction % complex
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Complex test`() {
		functionStack.pow(complex)
	}

	@Test
	fun validPowComplexTest() {
		assertEquals("((x^2) * (x^2)) / (-6.05 + 14.52i)", fraction.pow(SetNumber(2)).toString())
	}
}