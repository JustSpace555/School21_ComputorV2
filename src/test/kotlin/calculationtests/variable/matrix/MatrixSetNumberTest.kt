package calculationtests.variable.matrix

import models.exceptions.computorv2.calcexception.variable.DeterminantIsZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.parser

class MatrixSetNumberTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] + 0.1")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus T SetNumber test`() {
		parser("T([[1, 2]; [3, 4]]) + 0.1")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus RET SetNumber test`() {
		parser("REV([[1, 2]; [3, 4]]) + 0.1")
	}

	@Test
	fun validPlusDetMatrixTest() {
		assertEquals("-1.9", parser("DET([[1, 2]; [3, 4]]) + 0.1"))
	}

	@Test
	fun validPlusDetMatrix4x4Test() {
		assertEquals("0.1", parser("DET($matrix4x4Str) + 0.1"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus plus SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] ++ 0.1")
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail minus SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] - 0.1")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus T SetNumber test`() {
		parser("T([[1, 2]; [3, 4]]) - 0.1")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus RET SetNumber test`() {
		parser("REV([[1, 2]; [3, 4]]) - 0.1")
	}

	@Test
	fun validMinusDetMatrixTest() {
		assertEquals("-2.1", parser("DET([[1, 2]; [3, 4]]) - 0.1"))
	}

	@Test
	fun validMinusDetMatrix4x4Test() {
		assertEquals("-0.1", parser("DET($matrix4x4Str) - 0.1"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus minus SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] -- 0.1")
	}



	@Test
	fun validTimesTSetNumberTest() {
		assertEquals(
				"[ 0.1, 0.5, 0.9, 1.3 ]\n[ 0.2, 0.6, 1, 1.4 ]\n[ 0.3, 0.7, 1.1, 1.5 ]\n[ 0.4, 0.8, 1.2, 1.6 ]\n",
				parser("T($matrix4x4Str) * 0.1")
		)
	}

	@Test(expected = DeterminantIsZeroException::class)
	fun `fail times RET SetNumber test`() {
		parser("REV($matrix4x4Str) + 0.1")
	}

	@Test
	fun validTimesDetMatrixTest() {
		assertEquals("-0.2", parser("DET([[1, 2]; [3, 4]]) * 0.1"))
	}

	@Test
	fun validTimesDetMatrix4x4Test() {
		assertEquals("0", parser("DET($matrix4x4Str) * 0.1"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times times SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] ** 0.1")
	}

	@Test
	fun validTimesSetNumberTest() {
		assertEquals("[ 0.1, 0.2 ]\n[ 0.3, 0.4 ]\n", parser("[[1, 2] ; [3, 4]] * 0.1"))
	}



	@Test
	fun validDivTSetNumberTest() {
		assertEquals(
				"[ 10, 50, 90, 130 ]\n[ 20, 60, 100, 140 ]\n[ 30, 70, 110, 150 ]\n[ 40, 80, 120, 160 ]\n",
				parser("T($matrix4x4Str) / 0.1")
		)
	}

	@Test(expected = DeterminantIsZeroException::class)
	fun `fail div RET SetNumber test`() {
		parser("REV($matrix4x4Str) / 0.1")
	}

	@Test
	fun validDivDetMatrixTest() {
		assertEquals("-20", parser("DET([[1, 2]; [3, 4]]) / 0.1"))
	}

	@Test
	fun validDivDetMatrix4x4Test() {
		assertEquals("0", parser("DET($matrix4x4Str) / 0.1"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] // 0.1")
	}

	@Test
	fun validDivSetNumberTest() {
		assertEquals("[ 10, 20 ]\n[ 30, 40 ]\n", parser("[[1, 2] ; [3, 4]] / 0.1"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] % 0.1")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test`() {
		parser("[[1, 2] ; [3, 4]] ^ 0.1")
	}

	@Test
	fun validPowSetNumberTest() {
		assertEquals("[ 7, 10 ]\n[ 15, 22 ]\n", parser("[[1, 2] ; [3, 4]] ^ 2"))
	}
}