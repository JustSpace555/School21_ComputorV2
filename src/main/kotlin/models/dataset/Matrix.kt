package models.dataset

class Matrix(val input: Array<Array<Number>>) : DataSet {
	val n: Int = input.size
	val m: Int = input[0].size
}