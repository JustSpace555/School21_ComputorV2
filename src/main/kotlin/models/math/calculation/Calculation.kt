package models.math.calculation

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.math.MathExpression
import models.math.dataset.DataSet

data class Calculation(val input: List<String>): MathExpression {

	operator fun invoke(): DataSet = calcPolishNotation(convertToPolishNotation(input))

}