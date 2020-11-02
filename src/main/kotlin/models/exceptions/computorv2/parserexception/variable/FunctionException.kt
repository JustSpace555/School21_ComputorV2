package models.exceptions.computorv2.parserexception.variable

import models.exceptions.computorv2.ComputorV2Exception

@ComputorV2Exception
abstract class FunctionException: VariableParserException()

@ComputorV2Exception
class WrongFunctionBracketsFormatException : FunctionException() {
	override val message: String = "Wrong function brackets format"
}

@ComputorV2Exception
class MultipleArgumentException : FunctionException() {
	override val message: String = "Multiple function arguments. Max = 1"
}

@ComputorV2Exception
class IllegalFunctionElement : FunctionException() {
	override val message: String = "Illegal element in function"
}