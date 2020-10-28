package calculationtests.variable.matrix

import models.exception.calcexception.variable.IllegalOperationException
import models.exception.calcexception.variable.WrongMatrixSizeOperationException
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixMinusExpressionTest : MatrixExpressionsTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail minus test with SetNumber`() {
		"[[1, 2] ; [3, 4]]".getMatrix() - SetNumber()
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus test with Complex`() {
		"[[1, 2] ; [3, 4]]".getMatrix() - Complex(1, 1)
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus test with wrong amount of rows matrix`() {
		"[[1, 2] ; [3, 4]]".getMatrix() - "[[1, 2]]".getMatrix()
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail minus test with wrong amount of columns matrix`() {
		"[[1, 2] ; [3, 4]]".getMatrix() - "[[1] ; [3]]".getMatrix()
	}

	@Test
	fun validMinusTest() {
		assertEquals(
			"[[1, 2]; [3, 4]]".getMatrix(), "[[0, 1] ; [4, 6]]".getMatrix() - "[[-1, -1]; [1, 2]]".getMatrix()
		)

		assertEquals(
			"[[1.1, 2.2] ; [3.3, 4.4]]".getMatrix(),
			"[[1, 3.1] ; [3.3, 5]]".getMatrix() - "[[-0.1, 0.9] ; [0, 0.6]".getMatrix()
		)

		assertEquals(
			"[[1.1i, 1 - 3i]; [-5 - 5.1i, 3]]".getMatrix(),
			"[[2i, 1]; [2 + 3i, 1.5]]".getMatrix() - "[[0.9i, 3i]; [7 + 8.1i, -1.5]]".getMatrix()
		)
	}
}