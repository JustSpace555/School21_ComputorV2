package parser.variable

import computation.polishnotation.extensions.compute
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.parserexception.variable.EmptyMatrixArgumentException
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
				else {
					val computedEl = splittedElement.compute()
					if (computedEl !is Numeric) throw IllegalOperationException(Matrix::class, computedEl::class)
					computedEl
				}
			)
		}
		matrix.add(newRow)
	}

	val size = matrix.first().size
	matrix.forEach { if (it.size != size) throw EmptyMatrixArgumentException() }

	return matrix
}