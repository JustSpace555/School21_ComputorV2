package calculationtests.variable.numeric

import ComputorTest
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test
import parser.variable.numeric.toNumeric

class ComplexExpressionsTest : ComputorTest() {
	
	private val tempMatrix = Matrix(listOf(
		listOf("-1.1".toNumeric(), "-1.1i".toNumeric())
	))
	private val tempComplex = Complex(1, 1)


	@Test(expected = IllegalOperationException::class)
	fun `fail plus test with matrix`() {
		tempComplex + tempMatrix
	}

	@Test
	fun validPlusTest() {
		assertEquals(Complex(1.1, 1), Complex(0, 1) + SetNumber(1.1))
		assertEquals(SetNumber(1), Complex(1, -1) + Complex(imaginary = 1))

		assertEquals(Complex(1 ,1), Complex(1, 0) + Complex(0, 1))
		assertEquals(Complex(-1.1 ,-0.9), Complex(1, 1.1) + Complex(-2.1, -2))
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail minus test with matrix`() {
		tempComplex - tempMatrix
	}

	@Test
	fun validMinusTest() {
		assertEquals(Complex(-0.1, 2), Complex(0, 2) - SetNumber(0.1))
		assertEquals(SetNumber(1), Complex(1, 1) - Complex(imaginary = 1))

		assertEquals(Complex(0, 2), Complex(2, 2) - Complex(2, 0))
		assertEquals(Complex(1.1, 1.1), Complex(0, 0) - Complex(-1.1, -1.1))

	}



	@Test
	fun validTimesTest() {
		assertEquals(Complex(10, 10), Complex(2, 2) * SetNumber(5))
		assertEquals(Complex(10.2, 10.71), Complex(2, 2.1) * SetNumber(5.1))

		assertEquals(SetNumber(4), Complex(1, 1) * Complex(2, -2))
		assertEquals(Complex(imaginary = -4.4), Complex(2, -2) * Complex(1.1, -1.1))
		assertEquals(SetNumber(4.84), Complex(1.1, -1.1) * Complex(2.2, 2.2))
		assertEquals(Complex(-1, 3), Complex(1, 1) * Complex(1, 2))
		assertEquals(SetNumber(-9), Complex(imaginary = 3) * Complex(imaginary = 3))
	}



	@Test(expected = DivideByZeroException::class)
	fun `fail divide test with zero SetNumber`() {
		tempComplex / SetNumber(0)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail divide test with Matrix`() {
		tempComplex / tempMatrix
	}

	@Test
	fun validDivisionTest() {
		assertEquals(Complex(1, 1), Complex(2, 2) / SetNumber(2))
		assertEquals(Complex(1.1, 1.1), Complex(1.21, 1.21) / SetNumber(1.1))

		assertEquals(SetNumber(1), Complex(5, -5) / Complex(5, -5))
		assertEquals(Complex(imaginary = 0.4), Complex(2, 2) / Complex(5, -5))
		assertEquals(Complex(-0.149418849, -0.616440274), Complex(3.2, -4.3) / Complex(5.4, 6.5))
	}
}