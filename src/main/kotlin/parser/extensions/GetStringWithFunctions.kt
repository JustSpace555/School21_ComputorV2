package parser.extensions

import computorv1.parser.extensions.putSpacesComputorV1
import computorv1.reducedString
import models.dataset.Function
import models.variables

//TODO Подумать как переписать
fun getStringWithFunctions(input: List<String>): List<String> {
	var i = 0
	val output = mutableListOf<String>()

	while (i in input.indices) {
		if (variables.containsKey(input[i]) && variables[input[i]] is Function) {
			val function = variables[input[i]] as Function
			output.addAll(putSpacesComputorV1(function.function.reducedString(function.parameter)).split(' '))
			i += 4
			continue
		}
		output.add(input[i++])
	}
	return output
}