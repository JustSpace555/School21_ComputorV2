package models.exceptions.computorv2.parserexception.variable

import models.exceptions.computorv2.ComputorV2Exception

@ComputorV2Exception
abstract class MatrixException : VariableParserException()

@ComputorV2Exception
class WrongMatrixBracketsFormatException : MatrixException() {
	override val message: String = "Wrong brackets format"
}

@ComputorV2Exception
class EmptyMatrixArgumentException : MatrixException() {
	override val message: String = "Empty argument in matrix"
}

@ComputorV2Exception
class MatrixArgumentFormatException(input: Any? = null) : MatrixException() {
	override val message: String = "Matrix argument format exception. " + input?.let {
		"Element $it can't be stored in matrix"
	}
}