package models.exception.parserexception.variable

abstract class FunctionException: VariableParserException()

class WrongFunctionBracketsFormatException : FunctionException() {
	override val message: String = "Wrong function brackets format"
}

class MultipleArgumentException : FunctionException() {
	override val message: String = "Multiple function arguments. Max = 1"
}

class IllegalFunctionElement : FunctionException() {
	override val message: String = "Illegal element in function"
}