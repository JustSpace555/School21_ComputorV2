package computation.polishnotation.extensions

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.math.dataset.Brackets
import models.math.dataset.DataSet

fun List<String>.compute(parameter: String = ""): DataSet =
	calcPolishNotation(convertToPolishNotation(this, parameter))

fun DataSet.getList(): List<DataSet> = when(this) {
	!is Brackets -> listOf(this)
	else -> this.listOfOperands
}