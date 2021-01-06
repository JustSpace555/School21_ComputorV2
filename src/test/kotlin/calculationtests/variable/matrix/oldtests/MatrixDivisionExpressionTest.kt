package calculationtests.variable.matrix.oldtests

import calculationtests.variable.matrix.MatrixExpressionTest
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.calcexception.variable.WrongMatrixSizeOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixDivisionExpressionTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail division test with matrix`() {
		"[[1]; [3]]".getMatrix() / "[[1, 2] ; [3, 4]]".getMatrix()
	}

	@Test
	fun validTests() {

		assertEquals(
			"[[1, 2] ; [3,4]]".getMatrix(),
			"[[2,4];[6,8]]".getMatrix() / SetNumber(2)
		)

		assertEquals(
			"[[1.1, 2.2] ; [3.3, 4.4]]".getMatrix(),
			"[[1.21,2.42];[3.63,4.84]]".getMatrix() / SetNumber(1.1)
		)

		assertEquals(
			"[[0.5, 1]; [1.5,2]]".getMatrix(),
			"[[1 + 1i, 2 + 2i] ; [3 + 3i, 4 + 4i]]".getMatrix() / Complex(2, 2)
		)
	}
}