package models.exceptions.computorv2.calcexception.variable

import models.exceptions.computorv2.ComputorV2Exception
import models.dataset.Matrix

@ComputorV2Exception
abstract class MatrixCalculationException : VariableCalculationException()

@ComputorV2Exception
class WrongMatrixSizeOperationException(
	matrix1: Matrix,
	matrix2: Matrix,
	`fun`: Char
) : MatrixCalculationException() {
	override val message: String = "Unacceptable operation \"$`fun`\" between " +
			"matrix (${matrix1.rows} x ${matrix1.columns}) and matrix (${matrix2.rows} x ${matrix2.columns})"
}

@ComputorV2Exception
class NotSquareMatrixException : MatrixCalculationException() {
	override val message: String = "Matrix must be square to perform this operation"
}