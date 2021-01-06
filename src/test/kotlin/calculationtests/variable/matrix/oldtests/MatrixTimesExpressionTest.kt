package calculationtests.variable.matrix.oldtests

import calculationtests.variable.matrix.MatrixExpressionTest
import models.exceptions.computorv2.calcexception.variable.WrongMatrixSizeOperationException
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixTimesExpressionTest : MatrixExpressionTest() {

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail times test with wrong amount rows and columns`() {
		"[[1]; [3]]".getMatrix() * "[[1, 2] ; [3, 4]]".getMatrix()
	}

	@Test
	fun validTests() {

		assertEquals(
			"[[1, 2] ; [3,4]]".getMatrix(),
			"[[1,2];[3,4]]".getMatrix() * SetNumber(1)
		)

		assertEquals(
			"[[1.1, 2.2] ; [3.3, 4.4]]".getMatrix(),
			"[[1,2];[3,4]]".getMatrix() * SetNumber(1.1)
		)

		assertEquals(
			"[[1 + 1i, 2 + 2i] ; [3 + 3i, 4 + 4i]]".getMatrix(),
			"[[1,2];[3,4]]".getMatrix() * Complex(1, 1)
		)

		assertEquals(
			"[[1.1 - 1.1i, 2.2 - 2.2i] ; [3.3 - 3.3i, 4.4 - 4.4i]]".getMatrix(),
			"[[1,2];[3,4]]".getMatrix() * Complex(1.1, -1.1)
		)

		assertEquals(
			"[[7];[15]]".getMatrix(),
			"[[1, 2] ; [3, 4]]".getMatrix() * "[[1]; [3]]".getMatrix()
		)

		assertEquals(
			"[[1.5, 3.4] ; [1.65, 3.74] ; [-53.95, -70.94]; [6.5, 8.6]]".getMatrix(),
			"[[1, 2, 3, 4] ; [1.1, 2.2, 3.3, 4.4]; [-5, -5.5, -7, -1.1]; [1, 0, 1, 0]]".getMatrix() *
			"[[1, 2]; [3, 4]; [5.5, 6.6]; [-5.5, -6.6]]".getMatrix()
		)

		assertEquals(
			"[[1.21 - 1.21i, 4.4 + 2.2i]]".getMatrix(),
			"[[1.1 - 1.1i]]".getMatrix() * "[[1.1, 1 + 3i]]".getMatrix()
		)
	}
}