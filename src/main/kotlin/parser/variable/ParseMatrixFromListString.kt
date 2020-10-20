package parser.variable

import models.math.dataset.numeric.Numeric
import parser.variable.numeric.toNumeric

fun parseMatrixFromListString(input: List<String>): List<List<Numeric>> {
	val matrix = mutableListOf<List<Numeric>>()

	val filteredInput = input.filter { it != "[" && it != "]" }.joinToString("").split(";")

	for (row in filteredInput) {
		val rowElements = row.split(',')
		val newRow = mutableListOf<Numeric>()
		//TODO обработка математических выражений
		for (element in rowElements) newRow.add(element.toNumeric())
		matrix.add(newRow)
	}

	return matrix
}