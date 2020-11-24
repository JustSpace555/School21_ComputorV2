package parser.extensions

import models.dataset.function.Function
import models.history
import models.variables

fun getVariablesList(): String {

	if (variables.isEmpty()) return "There is no variables in memory."

	val output = StringBuilder()

	variables.forEach {
		when(it.value) {
			is Function -> {
				val function = it.value as Function
				output.append("${it.key}(${function.parameter}) = $function\n")
			}
			else -> output.append("${it.key} = ${it.value}\n")
		}
	}

	return output.toString()
}

fun getHistory(): String {

	if (history.isEmpty()) return "History of commands is empty."

	val output = StringBuilder()

	history.forEachIndexed { i: Int, command: Pair<String, String> ->
		output.append(
			"\nCommand #${i + 1} {" +
			"\n\tInput: ${command.first}" +
			"\n\tResult: ${command.second.replace("\n", "\n\t")}" +
			"\n}\n"
		)
	}

	return output.toString()
}

fun addToHistory(input: String, result: String) = history.add(Pair(input, result))