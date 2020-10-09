package parser

fun putSpaces(input: String): String {
	val output = StringBuilder()
	val listOfOperations = listOf('+', '-', '*', '/', '%', '^', '(', ')', '=', '?')
	val getWhiteSpace: (Boolean) -> String = { if (it) "" else " " }

	var beforeWhiteSpace: String
	var afterWhiteSpace: String
	var lastToken: Char = input.first()

	input.forEachIndexed { i: Int, c: Char ->
		when (c) {
			' ' -> return@forEachIndexed

			in listOfOperations -> {
				beforeWhiteSpace = getWhiteSpace(output.isNotEmpty() && output.last() == ' ')
				afterWhiteSpace = getWhiteSpace(c == '-' && (i == 0 || lastToken in listOfOperations))
			}

			else -> {
				beforeWhiteSpace = ""
				afterWhiteSpace = getWhiteSpace(!(i + 1 in input.indices && input[i + 1] == ' '))
			}
		}
		lastToken = c
		output.append(beforeWhiteSpace + c + afterWhiteSpace)
	}
	return output.toString()
}