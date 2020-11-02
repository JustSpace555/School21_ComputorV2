package parsertests.variable.numeric

import models.exceptions.computorv2.parserexception.variable.SetNumericFormatException
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.variable.numeric.toNumeric

class ParseNumericFromStringTest {

	@Test(expected = SetNumericFormatException::class)
	fun `test with invalid SetNumber Int input`() {
		"345asld".toNumeric()
	}

	@Test(expected = SetNumericFormatException::class)
	fun `test with invalid SetNumber Double input`() {
		"345.a".toNumeric()
	}

	@Test(expected = SetNumericFormatException::class)
	fun `test with invalid Complex input`() {
		"i345".toNumeric()
	}

	@Test
	fun validTests() {
		assertEquals(SetNumber(1), "1".toNumeric())
		assertEquals(SetNumber(1.1), "1.1".toNumeric())
		assertEquals(SetNumber(-1123), "-1123".toNumeric())
		assertEquals(SetNumber(-121.132), "-121.132".toNumeric())

		assertEquals(Complex(imaginary = SetNumber(1)), "1i".toNumeric())
		assertEquals(Complex(imaginary = SetNumber(-1)), "-1i".toNumeric())
		assertEquals(Complex(imaginary = SetNumber(1.23)), "1.23i".toNumeric())
		assertEquals(Complex(imaginary = SetNumber(-1.234)), "-1.234i".toNumeric())
	}
}