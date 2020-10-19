package models.math.dataset

import models.math.dataset.numeric.Numeric

data class Matrix(val input: List<List<Numeric>>) : DataSet {
	val n: Int = input.size
	val m: Int = input.first().size
}