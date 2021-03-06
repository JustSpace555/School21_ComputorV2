package parser.extensions

import models.operationsCharList

fun putSpaces(input: String): String {
	val output = StringBuffer()
	val getWhiteSpace: Boolean.() -> String = { if (this) "" else " " }

	var beforeWhiteSpace: String
	var afterWhiteSpace: String
	var lastToken: Char = input.first()

	input.forEachIndexed { i: Int, c: Char ->
		when (c) {
			' ' -> return@forEachIndexed

			in operationsCharList -> {
				beforeWhiteSpace = (output.isNotEmpty() && output.last() == ' ').getWhiteSpace()
				afterWhiteSpace = (
						c == '-' &&
						(i == 0 || lastToken in operationsCharList && lastToken != ')' && lastToken != ']')
					).getWhiteSpace()
			}

			else -> {
				beforeWhiteSpace = ""
				afterWhiteSpace = (!(i + 1 in input.indices && input[i + 1] == ' ')).getWhiteSpace()
			}
		}
		lastToken = c
		output.append(beforeWhiteSpace + c + afterWhiteSpace)
	}
	return output.trim().toString()
}