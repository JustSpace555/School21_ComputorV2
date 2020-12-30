package models.dataset

import computation.sampleAbs
import computation.sampleMax
import globalextensions.minus
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.variable.*
import parser.variable.parseMatrixFromListString

data class Matrix(val elementsCollection: List<List<Numeric>>) : DataSet {
	val rows = elementsCollection.size
	val columns = elementsCollection.first().size
	val isSquare = rows == columns

	constructor(input: Array<String>) : this(parseMatrixFromListString(input))

	companion object {
		fun getIdentityMatrix(n: Int): Matrix {
			val newElementsList = mutableListOf<List<SetNumber>>()
			for (i in 0 until n) {
				val newElementsRow = mutableListOf<SetNumber>()
				for (j in 0 until n) {
					newElementsRow.add(if (i == j) SetNumber(1) else SetNumber(0))
				}
				newElementsList.add(newElementsRow)
			}

			return Matrix(newElementsList)
		}
	}

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

	override fun div(other: DataSet): Matrix =
		if (other !is Matrix) invokeMatrixOperation(other, Numeric::div)
		else this * other.reverse()

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, "%")

	override fun pow(other: DataSet): Matrix {
		if (other !is SetNumber || other.number !is Int)
			throw IllegalOperationException(this::class, other::class, "^")

		val degree = other.number as Int
		return when {
			degree == 0 -> {
				if (!isSquare) throw NonSquareMatrixException()
				getIdentityMatrix(rows)
			}
			degree == -1 -> reverse()
			degree < -1 -> throw IllegalOperationException(this::class, SetNumber::class, "^")
			else -> {
				var newMatrix = copy()
				repeat((other.number - 1) as Int) { newMatrix *= newMatrix }
				newMatrix
			}
		}
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

	fun det(): Double {
		if (!isSquare) throw NonSquareMatrixException()

		val newElements = copy().castElementsToDouble().map { it.toMutableList() }.toMutableList()

		for (step in 0 until rows - 1)
			for (row in step + 1 until rows) {
				val coefficient = -newElements[row][step] / newElements[step][step]
				for (col in step until rows)
					newElements[row][col] += newElements[step][col] * coefficient
			}

		var det = 1.0
		for (i in 0 until rows)
			det *= newElements[i][i]

		return det
	}

	/*
		По методу Ньютона - Шульца
	 */
	fun reverse(): Matrix {
		when {
			!isSquare -> throw NonSquareMatrixException()
			elementsCollection.flatten().any { it is Complex } -> throw ComplexNumbersInMatrixException()
			det() == 0.0 -> throw DeterminantIsZeroException()
		}

		val castedElements = castElementsToDouble()

		var n1 = 0.0
		var nInf = 0.0
		elementsCollection.forEachIndexed { row, list ->
			var rowSum = 0.0
			var colSum = 0.0
			list.forEachIndexed { col, _ ->
				rowSum += sampleAbs(castedElements[row][col])
				colSum += sampleAbs(castedElements[col][row])
			}
			n1 = sampleMax(colSum, n1)
			nInf = sampleMax(rowSum, nInf)
		}

		val newTransposedElements = (transposed() * SetNumber(1 / (n1 * nInf)))
		val doubleIdentityMatrix = getIdentityMatrix(rows) * SetNumber(2)

		var inv = newTransposedElements.copy()
		val eps = 0.001
		val mOne = SetNumber(-1)

		while (sampleAbs((this * inv).det() - 1) >= eps)
			inv *= (this * inv * mOne + doubleIdentityMatrix)

		return inv
	}

	private fun castElementsToDouble() =
		elementsCollection
			.flatten()
			.map { (it as SetNumber).number.toDouble() }
			.chunked(rows)

	private fun checkKClassAndRowsWithColumns(input: DataSet, operationChar: Char) {
		if (input !is Matrix) throw IllegalOperationException(this::class, input::class, operationChar.toString())

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