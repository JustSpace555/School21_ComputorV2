package models.exception.parserexception.variable

abstract class MatrixException : VariableException()

class WrongMatrixBracketsFormatException : MatrixException() {
	override val message: String = "Wrong brackets format"
}

class EmptyMatrixArgumentException : MatrixException() {
	override val message: String = "Empty argument in matrix"
}