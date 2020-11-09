package computorv1

import computorv1.models.PolynomialTerm
import models.math.FunctionBrackets
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class PolynomialTermTests {

	@Test
	fun toStringTest() {
		var t = PolynomialTerm(1, 2)
		assertEquals("1 * X^2", t.toString())

		t = PolynomialTerm()
		assertEquals("0 * X^0", t.toString())

		t = PolynomialTerm(2.3)
		assertEquals("2.3 * X^0", t.toString())


		t = PolynomialTerm(-1.1, 2)
		assertEquals("-1.1 * X^2", t.toString())
	}

	@Test
	fun testAdd() {
		assertEquals(PolynomialTerm(), PolynomialTerm(1.5) + PolynomialTerm(-1.5))
		assertEquals(PolynomialTerm(1, 2), PolynomialTerm(-2, 2) + PolynomialTerm(3, 2))

		val expected = FunctionBrackets(mutableListOf(
					PolynomialTerm(1, 2), PolynomialTerm(-1, 1), PolynomialTerm(3, 0)
				))

		val actual = PolynomialTerm(-1, 2) + PolynomialTerm(-2, 1)+ PolynomialTerm(5.5, 0) +
				PolynomialTerm(2, 2) + PolynomialTerm(1, 1) + PolynomialTerm(-2.5, 0)

		assertEquals(expected, actual)
	}

	@Test
	fun testMinus() {
		assertEquals(PolynomialTerm(3), PolynomialTerm(1.5) - PolynomialTerm(-1.5))
		assertEquals(PolynomialTerm(-5, 2), PolynomialTerm(-2, 2) - PolynomialTerm(3, 2))

		val expected = FunctionBrackets(mutableListOf(
			PolynomialTerm(1, 2), PolynomialTerm(-3, 1), PolynomialTerm(Complex(3, -3), 0)
		))

		val actual = PolynomialTerm(-1, 2) + PolynomialTerm(-2, 1) + PolynomialTerm(5.5, 0) +
				PolynomialTerm(2, 2) - PolynomialTerm(1, 1) - SetNumber(2.5) - Complex(imaginary = 3)

		assertEquals(expected, actual)
	}

	@Test
	fun testTimes() {
		assertEquals(PolynomialTerm(-2.25), PolynomialTerm(1.5) * PolynomialTerm(-1.5))
		assertEquals(PolynomialTerm(-6, 4), PolynomialTerm(-2, 2) * PolynomialTerm(3, 2))

		val expected = PolynomialTerm(11, 3)

		val actual = PolynomialTerm(-1, 2) * PolynomialTerm(-2, 1) * PolynomialTerm(5.5, 0)

		assertEquals(expected, actual)
	}

	@Test
	fun testDiv() {
		assertEquals(PolynomialTerm(-1), PolynomialTerm(1.5) / PolynomialTerm(-1.5))
		assertEquals(PolynomialTerm(-2, 0), PolynomialTerm(-2, 2) / PolynomialTerm(1, 2))

		val expected = PolynomialTerm(1, 3)

		val actual = PolynomialTerm(-1, 2) * PolynomialTerm(-2, 1) / PolynomialTerm(2, 0)

		assertEquals(expected, actual)
	}
}
