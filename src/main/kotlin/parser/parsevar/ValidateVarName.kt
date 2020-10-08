package parser.parsevar

import models.exception.ExceptionMessages
import models.exception.ParserException

fun validateVarName(input: String): String {
	if (input.contains("[0-9]"))
		throw ParserException(ExceptionMessages.INVALID_VARIABLE_NAME)
	return input.toLowerCase()
}