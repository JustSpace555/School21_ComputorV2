package computation.polishnotation.extensions

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation

fun List<String>.compute() = calcPolishNotation(convertToPolishNotation(this))