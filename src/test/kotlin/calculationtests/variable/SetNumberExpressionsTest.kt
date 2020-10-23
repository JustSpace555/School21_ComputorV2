package calculationtests.variable

import models.exception.calcexception.DivideByZeroException
import models.exception.calcexception.variable.IllegalOperationException
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.variable.numeric.toNumeric

class SetNumberExpressionsTest {

	private val tempMatrix = Matrix(listOf(
		listOf("-1.1".toNumeric(), "-1.1i".toNumeric())
	))



	@Test(expected = IllegalOperationException::class)
	fun `fail plus test with matrix`() {
		SetNumber(1) + tempMatrix
	}

	@Test
	fun validPlusTest() {
		assertEquals(SetNumber(2), SetNumber(1) + 1)
		assertEquals(SetNumber(2.1), SetNumber(1) + 1.1)
		assertEquals(SetNumber(-2), SetNumber(1) + (-3))
		assertEquals(SetNumber(-2.1), SetNumber(1) + (-3.1))

		assertEquals(Complex(2, 2), SetNumber(2) + Complex(imaginary = 2))
		assertEquals(Complex(-2, 2), SetNumber(-2) + Complex(imaginary = 2))
		assertEquals(Complex(-2.2, 2), SetNumber(-2.2) + Complex(imaginary = 2))
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail minus test with matrix`() {
		SetNumber(1) - tempMatrix
	}

	@Test
	fun validMinusTest() {
		assertEquals(SetNumber(2), SetNumber(4) - 2)
		assertEquals(SetNumber(2), SetNumber(4.1) - 2.1)
		assertEquals(SetNumber(-2), SetNumber(-3) - (-1))
		assertEquals(SetNumber(-2.1), SetNumber(0) - 2.1)

		assertEquals(Complex(2, -2), SetNumber(2) - Complex(imaginary = 2))
		assertEquals(Complex(-2, -2), SetNumber(-2) - Complex(imaginary = 2))
		assertEquals(Complex(-2.2, 2), SetNumber(-2.2) - Complex(imaginary = -2))
		assertEquals(Complex(-0.2, 2), SetNumber(-2.2) - Complex(-2, -2))
	}



	@Test
	fun validTimesTest() {
		assertEquals(SetNumber(0), SetNumber(1) * 0)
		assertEquals(SetNumber(-1), SetNumber(1) * (-1))
		assertEquals(SetNumber(-2.25), SetNumber(1.5) * (-1.5))

		assertEquals(Complex(imaginary = -2), SetNumber(2) * Complex(imaginary = -1))
		assertEquals(Complex(4.4, -4.4), SetNumber(-2) * Complex(-2.2, 2.2))
		assertEquals(SetNumber(0), SetNumber(0) * Complex(1, 1))
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail divide test with Complex`() {
		SetNumber(1) / Complex(1, 1)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail divide test with Matrix`() {
		SetNumber(1) / tempMatrix
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail divide test with division by zero Int`() {
		SetNumber(1) / SetNumber(0)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail divide test with division by zero Double`() {
		SetNumber(1) / SetNumber(0.0)
	}

	@Test
	fun validDivisionTest() {
		assertEquals(SetNumber(1), SetNumber(-1.1) / (-1.1))
		assertEquals(SetNumber(-1), SetNumber(-1) / 1)
		assertEquals(SetNumber(2.2), SetNumber(4.4) / 2)
		assertEquals(SetNumber(), SetNumber() / 2)
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail rem test with Complex`() {
		SetNumber(1) % Complex(1, 1)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem test with Matrix`() {
		SetNumber(1) % tempMatrix
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail rem test with division by zero Int`() {
		SetNumber(1) % SetNumber(0)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail rem test with division by zero Double`() {
		SetNumber(1) % SetNumber(0.0)
	}

	@Test
	fun validRemTest() {
		assertEquals(SetNumber(1), SetNumber(3) % 2)
	}
}