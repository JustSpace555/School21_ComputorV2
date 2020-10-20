package models.math.dataset

import models.exception.calcexception.IllegalOperationException
import models.exception.calcexception.variable.UnavailableOperation
import models.exception.calcexception.variable.WrongMatrixSizeOperationException
import models.math.dataset.numeric.Numeric
import parser.variable.parseMatrixFromListString

data class Matrix(val input: List<List<Numeric>>) : DataSet {
	val rows: Int = input.size
	val columns: Int = input.first().size

	constructor(input: ArrayList<String>) : this(parseMatrixFromListString(input))

	operator fun get(i: Int, j: Int): Numeric = input[i][j]

	override fun plus(input: DataSet): DataSet {
		inputKClassCheck(input, '+')
		return invokeMatrixOperation(input as Matrix) { a: Numeric, b: Numeric -> a + b }
	}

	override fun minus(input: DataSet): DataSet {
		inputKClassCheck(input, '-')
		if (rows != input)
			throw WrongMatrixSizeOperationException(this, input, operationChar)
		return invokeMatrixOperation(input as Matrix) { a: Numeric, b: Numeric -> a - b }
	}

	override fun times(input: DataSet): DataSet {

	}

	override fun div(input: DataSet): DataSet {
		TODO("Not yet implemented")
	}

	override fun rem(input: DataSet): DataSet = throw UnavailableOperation(this::class, input::class, '%')



	private fun inputKClassCheck(input: DataSet, operationChar: Char) {
		if (input !is Matrix) throw IllegalOperationException(this::class, input::class, operationChar)
	}

	private fun invokeMatrixOperation(inputMatrix: Matrix, operation: (Numeric, Numeric) -> DataSet) =
		copy(input = input.mapIndexed { i: Int, numericList: List<Numeric> ->
			numericList.mapIndexed { j: Int, element ->
				operation(element, inputMatrix[i, j]) as Numeric
			}
		})

}