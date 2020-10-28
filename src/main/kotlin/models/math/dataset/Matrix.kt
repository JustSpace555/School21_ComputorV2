package models.math.dataset

import models.exception.calcexception.variable.IllegalOperationException
import models.exception.calcexception.variable.WrongMatrixSizeOperationException
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import parser.variable.parseMatrixFromListString

data class Matrix(val elementsCollection: List<List<Numeric>>) : DataSet {
	val rows = elementsCollection.size
	val columns = elementsCollection.first().size
	val isSquare = rows == columns

	constructor(input: Array<String>) : this(parseMatrixFromListString(input))

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

		val newElementsCollection = mutableListOf<List<Numeric>>()

		for (i in 0 until rows) {
			val newElementsRow = mutableListOf<Numeric>()

			for (j in 0 until other.columns) {
				var newElement = SetNumber(0) as Numeric

				for (k in 0 until columns) {
					newElement = (newElement + elementsCollection[i][k] * other[k][j]) as Numeric
				}

				newElementsRow.add(newElement)
			}
			newElementsCollection.add(newElementsRow)
		}

		return Matrix(newElementsCollection)
	}

	override fun div(other: DataSet): Matrix {
		if (other !is Matrix) return invokeMatrixOperation(other, Numeric::div)
		throw IllegalOperationException(this::class, other::class, '/')
	}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	fun Matrix.transposed(): Matrix {
		val newElementsList = mutableListOf<List<Numeric>>()

		for (j in 0 until columns) {
			val newElementsRow = mutableListOf<Numeric>()
			for (i in 0 until rows) {
				newElementsRow.add(elementsCollection[i][j])
			}
			newElementsList.add(newElementsRow)
		}

		return Matrix(newElementsList)
	}

	override fun toString(): String {
		val builder = StringBuilder()
		elementsCollection.forEach { builder.append("[ " + it.joinToString(postfix = " ") + "]\n") }
		return builder.toString()
	}

	private fun checkKClassAndRowsWithColumns(input: DataSet, operationChar: Char) {
		if (input !is Matrix) throw IllegalOperationException(this::class, input::class, operationChar)

		if (rows != input.rows || columns != input.columns)
			throw WrongMatrixSizeOperationException(this, input, operationChar)
	}

	private fun invokeMatrixOperation(input: DataSet, operation: (Numeric, DataSet) -> DataSet) =
		copy(elementsCollection = elementsCollection.mapIndexed { i, numericList ->
			numericList.mapIndexed { j, element ->
				operation(element, if (input is Matrix) input[i][j] else input) as Numeric
			}
		})
}