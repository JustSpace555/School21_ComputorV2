package models.math.dataset

import computation.polishnotation.extensions.compute
import computorv1.models.PolynomialTerm
import computorv1.reducedString
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import parser.extensions.putSpaces
import parser.variable.toPolynomialList

data class Function(val parameter: String, val function: List<PolynomialTerm>, val name: String = "") : DataSet {

	private val listOfPolynomialsBeforeInvoke = mutableListOf<PolynomialTerm>()

	constructor(
		parameter: String,
		listOfOperands: Array<String>
	) : this(parameter, listOfOperands.toList().compute().toPolynomialList())

	override fun plus(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '+')
	override fun minus(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '-')
	override fun times(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '*')
	override fun div(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '/')
	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')
	override fun pow(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '^')

	operator fun invoke(operand: Number) = this(SetNumber(operand))
	operator fun invoke(operand: DataSet): Numeric {
		val rightOperand = if (listOfPolynomialsBeforeInvoke.isNotEmpty()) {
			var newOperand = SetNumber(0) as DataSet
			listOfPolynomialsBeforeInvoke.forEach { newOperand += it() }
			newOperand
		} else operand

		return putSpaces(function.joinToString("+") { it.toString().replace("X", rightOperand.toString()) })
			.split(' ')
			.filter { it.isNotEmpty() }
			.compute()
			.also { clearPolynomialsBeforeInvoke() } as Numeric
	}

	override fun toString(): String = if (listOfPolynomialsBeforeInvoke.isEmpty()) {
		function.reducedString()
	} else {
		"$name(${listOfPolynomialsBeforeInvoke.reducedString()})"
	}

	fun addPolynomialsBeforeInvoke(elements: List<PolynomialTerm>) =
		listOfPolynomialsBeforeInvoke.addAll(elements)

	fun clearPolynomialsBeforeInvoke() = listOfPolynomialsBeforeInvoke.clear()
}