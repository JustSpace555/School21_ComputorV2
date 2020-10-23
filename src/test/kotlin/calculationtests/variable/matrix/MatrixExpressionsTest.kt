package calculationtests.variable.matrix

import models.math.dataset.Matrix
import parser.extensions.putSpaces
import parser.variable.parseMatrixFromListString

abstract class MatrixExpressionsTest {
	protected fun String.getMatrix() = Matrix(
		parseMatrixFromListString(
			putSpaces(this).split(' ').toTypedArray()
		)
	)
}