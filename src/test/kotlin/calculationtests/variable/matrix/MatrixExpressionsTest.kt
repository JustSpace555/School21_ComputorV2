package calculationtests.variable.matrix

import models.dataset.Matrix
import parser.extensions.putSpaces

abstract class MatrixExpressionsTest {
	protected fun String.getMatrix() = Matrix(putSpaces(this).split(' ').toTypedArray())
}