package models.dataset

import computation.polishnotation.extensions.compute
import computorv1.models.PolynomialTerm
import globalextensions.isEmpty
import globalextensions.toPolynomialList
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.Fraction
import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import computorv1.reducedString
import models.dataset.wrapping.Wrapping
import parser.extensions.putSpaces

open class Function(
	open val parameter: String,
	open val function: List<PolynomialTerm>,
	val name: String = ""
) : DataSet {

	private val listOfPolynomialsBeforeInvoke = mutableListOf<PolynomialTerm>()

	constructor(
		parameter: String,
		listOfOperands: Array<String>
	) : this(parameter, listOfOperands.toList().compute(parameter).toPolynomialList())

	override fun plus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, "+")
			is Wrapping -> other + this
			is Function -> {
				if (this == other) FunctionStack(SetNumber(2), this) else Brackets(this, other)
			}
			else -> {
				if (other.isEmpty())
					this
				else
					Brackets(this, other)
			}
		}

	override fun minus(other: DataSet): DataSet =
		when(other) {
			is Matrix -> throw IllegalOperationException(this::class, other::class, "-")
			is Wrapping -> other * SetNumber(-1) + this
			is Function -> {
				if (this == other) SetNumber(0) else Brackets(this, other * SetNumber(-1))
			}
			else -> {
				if (other.isEmpty())
					this
				else
					Brackets(this, other * SetNumber(-1))
			}
		}

	override fun times(other: DataSet): DataSet =
		when {
			other.isEmpty() -> SetNumber()
			other is Matrix -> throw IllegalOperationException(this::class, other::class, "*")
			other is Wrapping -> other * this
			other is SetNumber && other.compareTo(1.0) == 0 -> this
			else -> FunctionStack(this, other)
		}

	override fun div(other: DataSet): DataSet =
		when {
			other.isEmpty() -> throw DivideByZeroException()

			other is Matrix -> throw IllegalOperationException(this::class, other::class, "/")
			other is Fraction -> Fraction(other.denominator * this, other.numerator).simplify()
			other is SetNumber && other.compareTo(1.0) == 0 -> this
			else -> Fraction(this, other).simplify()
		}

	override fun rem(other: DataSet): DataSet = throw IllegalOperationException(this::class, other::class, "%")

	override fun pow(other: DataSet): DataSet =
		when {
			other !is SetNumber || other.number !is Int -> {
				throw IllegalOperationException(this::class, other::class, "^")
			}
			other.number == 0 -> SetNumber(1)
			other.number == 1 -> this
			else -> {
				var number = other.number as Int
				val belowZero = number < 0
				if (belowZero) number *= -1

				var newStack = FunctionStack(this) as DataSet
				repeat(number - 1) { newStack *= newStack }
				if (belowZero) Fraction(SetNumber(1), newStack) else newStack
			}
	}

	operator fun invoke(operand: Number) = this(SetNumber(operand))
	operator fun invoke(operand: DataSet): Numeric {
		val rightOperand = if (listOfPolynomialsBeforeInvoke.isNotEmpty()) {
			var newOperand = SetNumber(0) as DataSet
			listOfPolynomialsBeforeInvoke.forEach { newOperand += it.number.pow(it.degree) }
			newOperand
		} else operand

		return putSpaces(
			function.reducedString(parameter)
				.replace(parameter, "($rightOperand)")
				.replace("-($rightOperand)" , "-1 * ($rightOperand)")
		)
			.split(' ')
			.compute()
			.also { clearPolynomialsBeforeInvoke() }
			as Numeric
	}

	override fun toString(): String = if (listOfPolynomialsBeforeInvoke.isEmpty()) {
		function.reducedString(parameter)
	} else {
		"$name(${listOfPolynomialsBeforeInvoke.reducedString()})"
	}

	fun addPolynomialsBeforeInvoke(elements: List<PolynomialTerm>) =
		listOfPolynomialsBeforeInvoke.addAll(elements)

	fun clearPolynomialsBeforeInvoke() = listOfPolynomialsBeforeInvoke.clear()

	override fun equals(other: Any?): Boolean =
		when {
			other == null -> false
			other !is Function -> false
			this === other -> true
			else -> name == other.name && parameter == other.parameter && function.size == other.function.size &&
					function.zip(other.function).any { (el1, el2) -> el1 == el2 }
		}

	override fun hashCode(): Int {
		var result = 17
		result = 31 * result + parameter.hashCode()
		result = 31 * result + function.hashCode()
		result = 31 * result + name.hashCode()
		return result
	}
}