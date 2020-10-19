package models.math.dataset

import models.math.MathExpression

interface DataSet: MathExpression {
	operator fun plus(input: DataSet): DataSet
	operator fun minus(input: DataSet): DataSet
	operator fun times(input: DataSet): DataSet
	operator fun div(input: DataSet): DataSet
	operator fun rem(input: DataSet): DataSet
}