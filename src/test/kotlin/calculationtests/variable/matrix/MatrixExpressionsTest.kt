package calculationtests.variable.matrix

import ComputorTest
import models.dataset.Matrix
import parser.extensions.putSpaces

abstract class MatrixExpressionsTest : ComputorTest() {
	protected fun String.getMatrix() = Matrix(putSpaces(this).split(' ').toTypedArray())
}