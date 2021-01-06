package calculationtests.variable.matrix

import ComputorTest
import models.dataset.Matrix
import parser.extensions.putSpaces

abstract class MatrixExpressionTest : ComputorTest() {
	protected fun String.getMatrix() = Matrix(this.getList().toTypedArray())

	protected val matrix4x4Str = "[[1, 2, 3, 4]; [5, 6, 7, 8]; [9, 10, 11, 12]; [13, 14, 15, 16]]"
	protected val matrix4x4 = Matrix(matrix4x4Str.getList().toTypedArray())
}