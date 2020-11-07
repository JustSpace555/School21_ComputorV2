package computation.extensions

import models.math.FunctionBrackets

fun simplifyBrackets(input: List<String>, parameter: String): FunctionBrackets {
	val output = FunctionBrackets(parameter)
	var i = 0

	while (i in input.indices) {
		if (input[i] == "(") {
			val indexOfLastBracket = input.subList(i, input.size).findLastBracket()
			output.listOfExpressions.add(
				simplifyBrackets(input.subList(i + 1,  i + indexOfLastBracket), parameter)
			)
			i += indexOfLastBracket + 1
		} else {
			output.listOfExpressions.add(input[i++])
		}
	}

	return output
}