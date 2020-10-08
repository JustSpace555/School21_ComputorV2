package parser

import models.exception.ExceptionMessages
import models.exception.ParserException

fun parser(input: String) {
	val mod = putSpaces(input).split(' ')

	if (mod.contains("=") && mod.indexOf("=") != mod.lastIndexOf("="))
		throw ParserException(ExceptionMessages.WRONG_NUMBER_EQUALS)

}