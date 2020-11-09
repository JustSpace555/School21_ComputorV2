package computation.extensions

import models.exceptions.computorv1.parserexception.WrongDegreeFormatException
import models.math.FunctionBrackets
import parser.variable.numeric.isNumeric

fun simplifyBrackets(input: List<String>, parameter: String): FunctionBrackets {
	val output = FunctionBrackets(parameter)
	var i = 0
	val newList = mutableListOf<String>()

	if (input.contains("^")) {
		while (i in input.indices) {

			if (input[i] == "^") {
				try {

					if (input[i - 1] == parameter && input[i + 1].isNumeric()) {
						newList.removeLast()
						newList.add("$parameter^${input[i + 1]}")
						i += 2
					} else {
						newList.add(input[i++])
					}

				} catch (exc: IndexOutOfBoundsException) {
					throw WrongDegreeFormatException()
				}
			} else {
				newList.add(input[i++])
			}
		}
	} else newList.addAll(input)

	i = 0
	while (i in newList.indices) {
		if (newList[i] == "(") {
			val indexOfLastBracket = newList.subList(i, newList.size).findLastBracket()
			output.listOfExpressions.add(
				simplifyBrackets(newList.subList(i + 1,  i + indexOfLastBracket), parameter)
			)
			i += indexOfLastBracket + 1
		} else {
			output.listOfExpressions.add(newList[i++])
		}
	}

	return output
}