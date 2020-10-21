package models.math.dataset

import models.math.MathExpression

interface DataSet: MathExpression {
	operator fun plus(other: DataSet): DataSet
	operator fun minus(other: DataSet): DataSet
	operator fun times(other: DataSet): DataSet
	operator fun div(other: DataSet): DataSet
	operator fun rem(other: DataSet): DataSet
}