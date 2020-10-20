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
		checkKClassAndRowsWithColumns(input, '+')
		return invokeMatrixOperation(input, Numeric::plus)
	}

	override fun minus(input: DataSet): DataSet {
		checkKClassAndRowsWithColumns(input, '-')
		return invokeMatrixOperation(input, Numeric::minus)
	}

	override fun times(input: DataSet): DataSet {
		if (input is Numeric) return invokeMatrixOperation(input, Numeric::times)

		input as Matrix
		if (columns != input.rows) throw WrongMatrixSizeOperationException(this, input, '*')
		
	}

	override fun div(input: DataSet): DataSet {
		if (input is Numeric) return invokeMatrixOperation(input, Numeric::div)
		TODO("Not yet implemented")
	}

	override fun rem(input: DataSet): DataSet = throw UnavailableOperation(this::class, input::class, '%')



	private fun checkKClassAndRowsWithColumns(input: DataSet, operationChar: Char) {
		if (input !is Matrix) throw IllegalOperationException(this::class, input::class, operationChar)

		if (rows != input.rows || columns != input.columns)
			throw WrongMatrixSizeOperationException(this, input, operationChar)
	}

	private fun invokeMatrixOperation(input: DataSet, operation: (Numeric, DataSet) -> DataSet) =
		if (input is Matrix) {
			copy(input = this.input.mapIndexed { i, numericList ->
				numericList.mapIndexed { j, element ->
					operation(element, input[i, j]) as Numeric
				}
			})
		} else {
			copy(input = this.input.map { numericList ->
				numericList.map { element ->
					operation(element, input as Numeric) as Numeric
				}
			})
		}
}