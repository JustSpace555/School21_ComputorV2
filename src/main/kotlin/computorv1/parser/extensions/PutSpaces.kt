package computorv1.parser.extensions

import java.lang.StringBuilder

fun putSpacesComputorV1(input: String): String {
	val output = StringBuilder("")

	input.forEachIndexed { i: Int, c: Char ->
		when (c) {
			' ' -> {}
			'+' -> { output.append(' ') }
			'=' -> output.append(" $c ")
			'-' -> {
					if (i + 1 in input.indices) {
						when (input[i + 1]) {
							' ' -> output.append(" $c")
							'x', 'X' -> output.append("-1*X")
							else -> output.append(c)
						}
					} else {
						output.append(c)
					}
			}

			else -> output.append(c)
		}
	}
	return output.toString()
}