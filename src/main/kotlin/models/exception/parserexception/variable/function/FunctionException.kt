package models.exception.parserexception.variable.function

import models.exception.parserexception.variable.VariableException

abstract class FunctionException: VariableException()

class WrongFunctionBracketsFormatException: FunctionException() {
	override val message: String = "Wrong function brackets format"
}

class MultipleArgumentException: FunctionException() {
	override val message: String = "Multiple function arguments. Max = 1"
}