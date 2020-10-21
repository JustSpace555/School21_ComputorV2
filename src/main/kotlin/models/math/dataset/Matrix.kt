package models.math.dataset

import models.exception.calcexception.IllegalOperationException
import models.exception.calcexception.variable.UnavailableOperation
import models.exception.calcexception.variable.WrongMatrixSizeOperationException
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import parser.variable.parseMatrixFromListString

data class Matrix(val elementsCollection: List<List<Numeric>>) : DataSet {
	val rows: Int = elementsCollection.size
	val columns: Int = elementsCollection.first().size

	constructor(input: ArrayList<String>) : this(parseMatrixFromListString(input))

	operator fun get(i: Int): List<Numeric> = elementsCollection[i]
	operator fun get(i: Int, j: Int): Numeric = elementsCollection[i][j]

	override fun plus(other: DataSet): Matrix {
		checkKClassAndRowsWithColumns(other, '+')
		return invokeMatrixOperation(other, Numeric::plus)
	}

	override fun minus(other: DataSet): Matrix {
		checkKClassAndRowsWithColumns(other, '-')
		return invokeMatrixOperation(other, Numeric::minus)
	}

	override fun times(other: DataSet): Matrix {
		if (other !is Matrix) return invokeMatrixOperation(other, Numeric::times)

		if (columns != other.rows) throw WrongMatrixSizeOperationException(this, other, '*')

		val newElementsCollection = mutableListOf<MutableList<Numeric>>()

		for (i in 0 until rows)
			for (j in 0 until other.columns)
				for (k in 0 until columns)
					newElementsCollection[i][j] =
						(newElementsCollection[i][j] + elementsCollection[i][k] * other[k][j]) as Numeric

		return Matrix(newElementsCollection)
	}

	override fun div(other: DataSet): Matrix {
		if (other !is Matrix) return invokeMatrixOperation(other, Numeric::div)
		TODO()
	}

	override fun rem(other: DataSet): DataSet = throw UnavailableOperation(this::class, other::class, '%')

	fun Matrix.transposed(): Matrix {
		val newElementsList = mutableListOf<MutableList<Numeric>>()

		for (j in 0 until columns)
			for (i in 0 until rows)
				newElementsList[i][j] = elementsCollection[i][j]

		return Matrix(newElementsList)
	}

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
			//TODO Если функция
			input as Numeric
			copy(elementsCollection = elementsCollection.map { numericList ->
				numericList.map { element ->
					operation(element, input) as Numeric
				}
			})
		}
}