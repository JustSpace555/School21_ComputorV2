package models.math.dataset

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.calcexception.variable.IllegalOperationException
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import models.putTempVariable
import parser.extensions.putSpaces

data class Function(val parameter: String, val function: List<String>) : DataSet {

	override fun plus(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '+')
	override fun minus(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '-')
	override fun times(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '*')
	override fun div(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '/')
	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	operator fun invoke(operand: Number) = this(SetNumber(operand))
	operator fun invoke(operand: DataSet): Numeric {
		val newFunctionList =
			putSpaces(
				function.joinToString("").replace(parameter, putTempVariable(operand))
			).split(' ')

		return calcPolishNotation(convertToPolishNotation(newFunctionList)) as Numeric
	}
}