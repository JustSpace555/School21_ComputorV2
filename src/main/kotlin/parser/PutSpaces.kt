package parser

import java.lang.StringBuilder

fun putSpaces(input: String): String {
	val output = StringBuilder()

	input.filter { it != ' ' }.forEachIndexed { i: Int, c: Char ->
		if (c == '-' && (i == 0 || !output.last().isLetterOrDigit())) {
			output.append("-")
		} else if (
			c == '-' || c == '+' || c == '*' || c == '/' || c == '^' || c == '%' || c == '='
		) {
			output.append(" $c ")
		} else {
			output.append(c)
		}
	}

	return output.toString()
}