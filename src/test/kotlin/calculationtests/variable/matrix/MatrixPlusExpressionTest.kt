package calculationtests.variable.matrix

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.calcexception.variable.WrongMatrixSizeOperationException
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test

class MatrixPlusExpressionTest : MatrixExpressionsTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus test with SetNumber`() {
		"[[1, 2] ; [3, 4]]".getMatrix() + SetNumber()
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail plus test with Complex`() {
		"[[1, 2] ; [3, 4]]".getMatrix() + Complex(1, 1)
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus test with wrong amount of rows matrix`() {
		"[[1, 2] ; [3, 4]]".getMatrix() + "[[1, 2]]".getMatrix()
	}

	@Test(expected = WrongMatrixSizeOperationException::class)
	fun `fail plus test with wrong amount of columns matrix`() {
		"[[1, 2] ; [3, 4]]".getMatrix() + "[[1] ; [3]]".getMatrix()
	}

	@Test
	fun validPlusTest() {
		assertEquals(
			"[[1, 2]; [3, 4]]".getMatrix(), "[[0, 1] ; [2, 3]]".getMatrix() + "[[1, 1]; [1, 1]]".getMatrix()
		)

		assertEquals(
			"[[1.1, 2.2] ; [3.3, 4.4]]".getMatrix(),
			"[[1, 2.1] ; [2.2, 4]]".getMatrix() + "[[0.1, 0.1] ; [1.1, 0.4]".getMatrix()
		)

		assertEquals(
			"[[1.1i, 1 + 3i]; [-5 - 5.1i, 3]]".getMatrix(),
			"[[1i, 1]; [-2 - 3i, 1.5]]".getMatrix() + "[[0.1i, 3i]; [-3 - 2.1i, 1.5]]".getMatrix()
		)
	}
}