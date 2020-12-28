package computorv1.parser.extensions

internal fun putSpaces(input: String): String {
	val output = StringBuilder()

	input.forEachIndexed { i: Int, c: Char ->
		when (c) {
			' ' -> {}
			'+' -> output.append(' ')
			'=' -> output.append(" $c ")
			'-' -> {
				if (i + 1 in input.indices && input[i + 1] == ' ')
					output.append(" $c")
				else
					output.append(c)
			}
			else -> output.append(c)
		}
	}
	return output.toString()
}