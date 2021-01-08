package calculationtests.variable.matrix

import models.dataset.numeric.Complex
import models.exceptions.computorv2.calcexception.variable.DeterminantIsZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test
import parser.parser

class MatrixComplexTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Complex test`() {
		parser("[[1, 2] ; [3, 4]] + ($complex)")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus T Complex test`() {
		parser("T([[1, 2]; [3, 4]]) + ($complex)")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus RET Complex test`() {
		parser("REV([[1, 2]; [3, 4]]) + ($complex)")
	}

	@Test
	fun validPlusDetMatrixTest() {
		assertEquals("0.2 + 3.3i", parser("DET([[1, 2]; [3, 4]]) + ($complex)"))
	}

	@Test
	fun validPlusDetMatrix4x4Test() {
		assertEquals("2.2 + 3.3i", parser("DET($matrix4x4Str) + ($complex)"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus plus Complex test`() {
		parser("[[1, 2] ; [3, 4]] ++ ($complex)")
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail minus Complex test`() {
		parser("[[1, 2] ; [3, 4]] - ($complex)")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus T Complex test`() {
		parser("T([[1, 2]; [3, 4]]) - ($complex)")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus RET Complex test`() {
		parser("REV([[1, 2]; [3, 4]]) - ($complex)")
	}

	@Test
	fun validMinusDetMatrixTest() {
		assertEquals("-4.2 - 3.3i", parser("DET([[1, 2]; [3, 4]]) - ($complex)"))
	}

	@Test
	fun validMinusDetMatrix4x4Test() {
		assertEquals("-2.2 - 3.3i", parser("DET($matrix4x4Str) - ($complex)"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus minus Complex test`() {
		parser("[[1, 2] ; [3, 4]] -- ($complex)")
	}



	@Test
	fun validTimesTComplexTest() {
		assertEquals(
				"[ 2.2 + 3.3i, 11 + 16.5i, 19.8 + 29.7i, 28.6 + 42.9i ]\n" +
						"[ 4.4 + 6.6i, 13.2 + 19.8i, 22 + 33i, 30.8 + 46.2i ]\n" +
						"[ 6.6 + 9.9i, 15.4 + 23.1i, 24.2 + 36.3i, 33 + 49.5i ]\n" +
						"[ 8.8 + 13.2i, 17.6 + 26.4i, 26.4 + 39.6i, 35.2 + 52.8i ]\n",
				parser("T($matrix4x4Str) * ($complex)")
		)
	}

	@Test(expected = DeterminantIsZeroException::class)
	fun `fail times RET Complex test`() {
		parser("REV($matrix4x4Str) + ($complex)")
	}

	@Test
	fun validTimesDetMatrixTest() {
		assertEquals("-4.4 - 6.6i", parser("DET([[1, 2]; [3, 4]]) * ($complex)"))
	}

	@Test
	fun validTimesDetMatrix4x4Test() {
		assertEquals("0", parser("DET($matrix4x4Str) * ($complex)"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times times Complex test`() {
		parser("[[1, 2] ; [3, 4]] ** ($complex)")
	}

	@Test
	fun validTimesComplexTest() {
		assertEquals(
				"[ 2.2 + 3.3i, 4.4 + 6.6i ]\n[ 6.6 + 9.9i, 8.8 + 13.2i ]\n",
				parser("[[1, 2] ; [3, 4]] * ($complex)")
		)
	}



	@Test(expected = IllegalOperationException::class)
	fun `fail Div T Complex Test`() {
		parser("T($matrix4x4Str) / ($complex)")
	}

	@Test(expected = DeterminantIsZeroException::class)
	fun `fail div RET Complex test`() {
		parser("REV($matrix4x4Str) / ($complex)")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail Div Det Matrix Test`() {
		parser("DET([[1, 2]; [3, 4]]) / ($complex)")
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail Div Det Matrix4x4 Test`() {
		assertEquals("0", parser("DET($matrix4x4Str) / ($complex)"))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Complex test`() {
		parser("[[1, 2] ; [3, 4]] // ($complex)")
	}

	@Test
	fun validDivComplexTest() {
		assertEquals(
				"[ 0.5 + 0.5i, 1 + i ]\n[ 1.5 + 1.5i, 2 + 2i ]\n",
				parser("[[i, 2i] ; [3i, 4i]] / (${Complex(1, 1)})")
		)
	}




	@Test(expected = IllegalOperationException::class)
	fun `fail rem Complex test`() {
		parser("[[1, 2] ; [3, 4]] % ($complex)")
	}




	@Test(expected = IllegalOperationException::class)
	fun `fail pow Complex test`() {
		parser("[[1, 2] ; [3, 4]] ^ ($complex)")
	}
}