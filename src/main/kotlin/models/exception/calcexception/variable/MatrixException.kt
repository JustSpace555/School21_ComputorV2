package models.exception.calcexception.variable

import models.math.dataset.Matrix

class WrongMatrixSizeOperationException(
		matrix1: Matrix,
		matrix2: Matrix,
		`fun`: Char
) : VariableCalculationException() {
	override val message: String = "Unacceptable operation \"$`fun`\" between " +
			"matrix (${matrix1.rows} x ${matrix1.columns}) and matrix (${matrix2.rows} x ${matrix2.columns}"
}