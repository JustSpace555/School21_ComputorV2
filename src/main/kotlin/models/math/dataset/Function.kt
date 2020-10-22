package models.math.dataset

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.calcexception.variable.IllegalOperationException
import models.math.dataset.numeric.Numeric

data class Function(val parameter: String, val function: List<String>) : DataSet {

	override fun plus(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '+')
	override fun minus(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '-')
	override fun times(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '*')
	override fun div(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '/')
	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	operator fun invoke(operand: Numeric): Numeric {
		val newFunctionList = function

		newFunctionList.toMutableList().replaceAll { if (it == parameter) operand.toString() else it }

		return calcPolishNotation(convertToPolishNotation(newFunctionList)) as Numeric
	}
}