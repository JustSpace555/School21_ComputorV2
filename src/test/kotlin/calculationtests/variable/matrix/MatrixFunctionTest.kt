package calculationtests.variable.matrix

import models.exceptions.computorv2.calcexception.variable.DeterminantIsZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test
import parser.parser

class MatrixFunctionTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Function test`() {
		matrix + function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Function test`() {
		matrix - function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Function test`() {
		matrix * function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Function test`() {
		matrix / function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Function test`() {
		matrix % function
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Function test`() {
		matrix.pow(function)
	}

	@Test
	fun testT() {
		assertEquals("[ 1, 3 ]\n[ 2, 4 ]\n", parser("T([[1,2];[3,4]])"))
		assertEquals("[ 1, 5, 9, 13 ]\n[ 2, 6, 10, 14 ]\n[ 3, 7, 11, 15 ]\n[ 4, 8, 12, 16 ]\n", parser("T($matrix4x4Str)"))
	}

	@Test
	fun testDet() {
		assertEquals("-2", parser("DET([[1, 2];[3,4]])"))
		assertEquals("0", parser("DET($matrix4x4Str)"))
	}

	@Test(expected = DeterminantIsZeroException::class)
	fun `fail test RET`() {
		parser("REV($matrix4x4Str)")
	}

	@Test
	fun testRet() {
		assertEquals("[ -2, 1 ]\n[ 1.5, -0.5 ]\n", parser("REV([[1, 2];[3,4]])"))
		assertEquals(
				"[ 0, -0.375, 0.125 ]\n[ 0, 0.25, 0.25 ]\n[ -0.5, -0.0625, 0.6874 ]\n",
				parser("REV([[3,4,-2];[-2, 1, 0];[2, 3, 0]])")
		)
	}
}