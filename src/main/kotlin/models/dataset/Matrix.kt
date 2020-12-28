package models.dataset

import globalextensions.minus
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.calcexception.variable.NotSquareMatrixException
import models.exceptions.computorv2.calcexception.variable.WrongMatrixSizeOperationException
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

	override fun pow(other: DataSet): Matrix {
		if (other !is SetNumber || other.number is Double)
			throw IllegalOperationException(this::class, other::class, '^')

		val degree = other.number as Int
		if (degree == 0) return getIdentityMatrix()
		else if (degree < 0) throw IllegalOperationException(this::class, SetNumber::class, '^')

		var newMatrix = this
		repeat((other.number - 1) as Int) { newMatrix *= newMatrix }
		return newMatrix
	}

	override fun toString(): String {
		val builder = StringBuilder()
		elementsCollection.forEach {
			builder.append("[ " + it.joinToString(postfix = ", ").removeSuffix(", ") + " ]\n")
		}
		return builder.toString()
	}

	fun transposed(): Matrix {
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

	fun getIdentityMatrix(): Matrix {
		if (!isSquare) throw NotSquareMatrixException()

		val newElementsList = mutableListOf<List<SetNumber>>()
		for (i in 0 until rows) {
			val newElementsRow = mutableListOf<SetNumber>()
			for (j in 0 until columns) {
				newElementsRow.add(if (i == j) SetNumber(1) else SetNumber(0))
			}
			newElementsList.add(newElementsRow)
		}

		return Matrix(newElementsList)
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