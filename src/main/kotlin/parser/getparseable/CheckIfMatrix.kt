package parser.getparseable

import models.exceptions.computorv2.parserexception.variable.EmptyMatrixArgumentException
import models.exceptions.computorv2.parserexception.variable.WrongMatrixBracketsFormatException

fun checkIfMatrix(afterEqual: List<String>): Boolean {
	val amountOfOpenBracket = afterEqual.count { it == "[" }
	val amountOfCloseBracket = afterEqual.count { it == "]" }

	when {
		amountOfOpenBracket == 0 && amountOfCloseBracket == 0 -> {
			if (afterEqual.contains(";"))
				throw WrongMatrixBracketsFormatException()
			else
				return false
		}

		amountOfOpenBracket * amountOfCloseBracket == 0 || amountOfOpenBracket != amountOfCloseBracket -> {
			throw WrongMatrixBracketsFormatException()
		}
	}

	val filteredString = afterEqual.filter { it == "[" || it == "]" }.joinToString("")
		.replace("[[", "")
		.replace("]]", "")
		.replace("][", "")
	if (filteredString.isNotEmpty()) throw WrongMatrixBracketsFormatException()

	afterEqual.forEachIndexed { i: Int, element: String ->
		if (element == "," && (
					i + 1 !in afterEqual.indices ||
					afterEqual[i - 1] == "[" ||
					afterEqual[i + 1].matches(Regex("[,\\]]"))
		)) {
				throw EmptyMatrixArgumentException()
		}
	}

	return true
}