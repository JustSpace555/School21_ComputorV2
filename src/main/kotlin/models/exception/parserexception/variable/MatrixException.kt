package models.exception.parserexception.variable

abstract class MatrixException : VariableParserException()

class WrongMatrixBracketsFormatException : MatrixException() {
	override val message: String = "Wrong brackets format"
}

class EmptyMatrixArgumentException : MatrixException() {
	override val message: String = "Empty argument in matrix"
}

class MatrixArgumentFormatException(input: Any? = null) : MatrixException() {
	override val message: String = "Matrix argument format exception" + input?.let {
		"Element $it can't be stored in matrix"
	}
}