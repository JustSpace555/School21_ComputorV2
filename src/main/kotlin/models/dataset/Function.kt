package models.dataset

import computation.polishnotation.extensions.compute
import computorv1.models.PolynomialTerm
import computorv1.reducedString
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.Fraction
import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import parser.extensions.putSpaces
import parser.variable.toPolynomialList

data class Function(val parameter: String, val function: List<PolynomialTerm>, val name: String = "") : DataSet {

	private val listOfPolynomialsBeforeInvoke = mutableListOf<PolynomialTerm>()

	constructor(
		parameter: String,
		listOfOperands: Array<String>
	) : this(parameter, listOfOperands.toList().compute(parameter).toPolynomialList())

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, '+')
			else -> {
				if (other is Numeric && other.isZero())
					this
				else
					Brackets(this, other)
			}
		}

	override fun minus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, '-')
			else -> {
				if (other is Numeric && other.isZero())
					this
				else
					Brackets(this, other * SetNumber(-1))
			}
		}

	override fun times(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, '*')
			else -> when {
				other is Numeric && other.isZero() -> SetNumber(0)
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> FunctionStack(this, other)
			}
		}

	override fun div(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, '/')
			else -> when {
				other is Numeric && other.isZero() -> throw DivideByZeroException()
				other is SetNumber && other.compareTo(1.0) == 0 -> this
				else -> Fraction(this, other)
			}
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, '%')

	override fun pow(other: DataSet): DataSet {
		if (other !is SetNumber || other.number !is Int || other < 0)
			throw IllegalOperationException(this::class, other::class, '^')

		val number = other.number as Int
		if (number == 0) return SetNumber(1)
		else if (number == 1) return this

		var newStack = FunctionStack(this)
		repeat(number - 1) { newStack = (newStack * newStack) as FunctionStack }
		return if (number < 0) Fraction(SetNumber(1), newStack) else newStack
	}

	operator fun invoke(operand: Number) = this(SetNumber(operand))
	operator fun invoke(operand: DataSet): Numeric {
		val rightOperand = if (listOfPolynomialsBeforeInvoke.isNotEmpty()) {
			var newOperand = SetNumber(0) as DataSet
			listOfPolynomialsBeforeInvoke.forEach { newOperand += it() }
			newOperand
		} else operand

		return putSpaces(
			function.reducedString(parameter)
				.replace(parameter, "($rightOperand)")
				.replace("-($rightOperand)" , "-1 * ($rightOperand)")
		)
			.split(' ')
			.compute()
			.also { clearPolynomialsBeforeInvoke() } as Numeric
	}

	override fun toString(): String = if (listOfPolynomialsBeforeInvoke.isEmpty()) {
		function.reducedString(parameter)
	} else {
		"$name(${listOfPolynomialsBeforeInvoke.reducedString()})"
	}

	fun addPolynomialsBeforeInvoke(elements: List<PolynomialTerm>) =
		listOfPolynomialsBeforeInvoke.addAll(elements)

	fun clearPolynomialsBeforeInvoke() = listOfPolynomialsBeforeInvoke.clear()
}