package parser.extensions

import models.basicOperationsStringList

fun List<String>.deleteSpacesFromMultipleOperations(): List<String> {
	val output = mutableListOf<String>()

	var i = 0
	while (i in indices) {
		if (this[i] in basicOperationsStringList && i + 1 in indices && this[i + 1] in basicOperationsStringList)
			output.add("${this[i++]}${this[i++]}")
		else
			output.add(this[i++])
	}

	return output.toList()
}