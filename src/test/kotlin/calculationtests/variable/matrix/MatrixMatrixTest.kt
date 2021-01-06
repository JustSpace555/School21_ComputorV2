package calculationtests.variable.matrix

import models.dataset.Matrix
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.calcexception.variable.WrongMatrixSizeOperationException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.parser

class MatrixMatrixTest : MatrixExpressionTest() {

	private val matrix3x2str = "[[1, 2]; [3, 4]; [5, 6]]"
	private val matrix2x3str = "[[1, 2 ,3]; [4, 5, 6]]"
	private val matrix2x3 = matrix2x3str.getMatrix()
	private val matrix3x2 = matrix3x2str.getMatrix()

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus Matrix test 1`() {
		matrix2x3 + matrix3x2
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus Matrix test 2`() {
		matrix3x2 + matrix2x3
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus Matrix test 3`() {
		matrix2x3 + matrix
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus Matrix test 4`() {
		matrix2x3 + matrix4x4
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus Matrix test 5`() {
		matrix + matrix4x4
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Matrix test`() {
		parser("[[1,2];[3,4]] + [[1,2];[3,4]]")
	}

	@Test
	fun validPlusMatrixTest() {
		assertEquals("[ 2, 4 ]\n[ 6, 8 ]\n", parser("[[1,2];[3,4]]++[[1,2];[3,4]]"))
	}



	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus Matrix test 1`() {
		matrix2x3 - matrix3x2
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus Matrix test 2`() {
		matrix3x2 - matrix2x3
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus Matrix test 3`() {
		matrix2x3 - matrix
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus Matrix test 4`() {
		matrix2x3 - matrix4x4
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus Matrix test 5`() {
		matrix - matrix4x4
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Matrix test`() {
		parser("[[1,2];[3,4]] - [[1,2];[3,4]]")
	}

	@Test
	fun validMinusMatrixTest() {
		assertEquals("[ 0, 0 ]\n[ 0, 0 ]\n", parser("[[1,2];[3,4]]--[[1,2];[3,4]]"))
		assertEquals(
			"[ 0, 0, 0, 0 ]\n[ 0, 0, 0, 0 ]\n[ 0, 0, 0, 0 ]\n[ 0, 0, 0, 0 ]\n",
			parser("$matrix4x4Str -- $matrix4x4Str")
		)
	}



	@Test
	fun validTimesTest1() {
		assertEquals("[ 22, 28 ]\n[ 49, 64 ]\n", parser("$matrix2x3str ** $matrix3x2str"))
		assertEquals("[ 9, 12, 15 ]\n[ 19, 26, 33 ]\n[ 29, 40, 51 ]\n", parser("$matrix3x2str ** $matrix2x3str"))
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail times Matrix test 1`() {
		matrix2x3 * matrix2x3
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail times Matrix test 2`() {
		matrix * matrix4x4
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Matrix test`() {
		parser("[[1,2];[3,4]] * [[1,2];[3,4]]")
	}

	@Test
	fun validTimesREVMatrixTest() {
		assertEquals(
			"${Matrix.getIdentityMatrix(3)}",
			parser("[[3,4,-2];[-2, 1, 0];[2, 3, 0]] ** REV([[3,4,-2];[-2, 1, 0];[2, 3, 0]])")
		)
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail div Matrix test 1`() {
		parser("$matrix3x2str // $matrix2x3str")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Matrix test 2`() {
		parser("[[1,2];[3,4]] / [[1,2];[3,4]]")
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail rem Matrix test`() {
		matrix % matrix4x4
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail pow Matrix test`() {
		matrix.pow(matrix)
	}
}