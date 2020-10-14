package parser.configurateparseable

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import variables

fun putSetNumber(name: String, afterEqual: List<String>) {

	val number =
		if (afterEqual.size > 1) calcPolishNotation(convertToPolishNotation(afterEqual))
		else calcPolishNotation(afterEqual)

	variables[name] = number
}