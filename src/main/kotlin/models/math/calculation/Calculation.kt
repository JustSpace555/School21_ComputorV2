package models.math.calculation

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.math.MathExpression
import models.math.dataset.numeric.SetNumber

data class Calculation(val input: List<String>): MathExpression {

	operator fun invoke(): SetNumber =
		calcPolishNotation(convertToPolishNotation(input))

}