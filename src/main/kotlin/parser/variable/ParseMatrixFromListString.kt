package parser.variable

import computation.polishnotation.extensions.compute
import models.exceptions.computorv2.parserexception.variable.EmptyMatrixArgumentException
import models.math.dataset.numeric.Numeric
import parser.extensions.putSpaces
import parser.variable.numeric.toNumeric

fun parseMatrixFromListString(input: Array<String>): List<List<Numeric>> {
	val matrix = mutableListOf<List<Numeric>>()

	val filteredInput = input.filter { it != "[" && it != "]" }.joinToString("").split(";")

	for (row in filteredInput) {
		val rowElements = row.split(',')
		val newRow = mutableListOf<Numeric>()
		for (element in rowElements) {
			val splittedElement = putSpaces(element).split(' ')
			newRow.add(
				if (splittedElement.size == 1) element.toNumeric()
				else splittedElement.compute() as Numeric
			)
		}
		matrix.add(newRow)
	}

	val size = matrix.first().size
	matrix.forEach { if (it.size != size) throw EmptyMatrixArgumentException() }

	return matrix
}