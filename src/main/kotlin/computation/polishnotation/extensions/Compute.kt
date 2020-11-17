package computation.polishnotation.extensions

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.dataset.DataSet

fun List<String>.compute(parameter: String = ""): DataSet =
	calcPolishNotation(convertToPolishNotation(this, parameter))