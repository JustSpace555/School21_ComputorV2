package models.math.calculation

import computation.polishnotation.extensions.compute
import models.math.MathExpression
import models.math.dataset.DataSet

data class Calculation(val input: List<String>): MathExpression {

	operator fun invoke(): DataSet = input.compute()

}