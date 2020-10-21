package models.math.dataset

import models.exception.calcexception.IllegalOperationException
import models.exception.calcexception.variable.UnavailableOperation
import models.exception.calcexception.variable.WrongMatrixSizeOperationException
import models.math.dataset.numeric.Numeric
import parser.variable.parseMatrixFromListString

data class Matrix(val elementsCollection: List<List<Numeric>>) : DataSet {
	val rows: Int = elementsCollection.size
	val columns: Int = elementsCollection.first().size

	constructor(input: ArrayList<String>) : this(parseMatrixFromListString(input))

	operator fun get(i: Int): List<Numeric> = elementsCollection[i]
	operator fun get(i: Int, j: Int): Numeric = elementsCollection[i][j]

	override fun plus(input: DataSet): Matrix {
		checkKClassAndRowsWithColumns(input, '+')
		return invokeMatrixOperation(input, Numeric::plus)
	}

	override fun minus(input: DataSet): Matrix {
		checkKClassAndRowsWithColumns(input, '-')
		return invokeMatrixOperation(input, Numeric::minus)
	}

	override fun times(input: DataSet): Matrix {
		if (input is Numeric) return invokeMatrixOperation(input, Numeric::times)

		input as Matrix
		if (columns != input.rows) throw WrongMatrixSizeOperationException(this, input, '*')

		val newElementsCollection = mutableListOf<MutableList<Numeric>>()

		for (i in 0 until rows)
			for (j in 0 until input.columns)
				for (k in 0 until columns)
					newElementsCollection[i][j] = (newElementsCollection[i][j] + elementsCollection[i][k] * input[k][j]) as Numeric

		return Matrix(newElementsCollection)
	}

	override fun div(input: DataSet): Matrix {
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
			copy(elementsCollection = elementsCollection.mapIndexed { i, numericList ->
				numericList.mapIndexed { j, element ->
					operation(element, input[i][j]) as Numeric
				}
			})
		} else {
			input as Numeric
			copy(elementsCollection = elementsCollection.map { numericList ->
				numericList.map { element ->
					operation(element, input) as Numeric
				}
			})
		}
}