package computorv1.models

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.math.FunctionBrackets
import models.math.dataset.DataSet
import models.math.dataset.Matrix
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber

data class PolynomialTerm(
	val number: Numeric = SetNumber(),
	var degree: Int = 0
) : DataSet {

	constructor(number: Number, degree: Int = 0) : this(SetNumber(number), degree)

	override fun plus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '+')

			is PolynomialTerm -> {
				if (other.degree == degree) {
					copy(number = (number + other.number) as Numeric)
				} else {
					FunctionBrackets().apply { addElements(this@PolynomialTerm, other) }
				}
			}

			is FunctionBrackets -> other + this

			else -> {
				other as Numeric
				if (degree == 0) {
					copy(number = (number + other) as Numeric)
				} else {
					FunctionBrackets().apply {
						addElements(this@PolynomialTerm, PolynomialTerm(other, 0))
					}
				}
			}
		}

	override fun minus(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '-')

			is PolynomialTerm -> {
				if (other.degree == degree) {
					copy(number = (number - other.number) as Numeric)
				} else {
					FunctionBrackets().apply {
						addElements(
							this@PolynomialTerm, other.copy(number = (other.number * SetNumber(-1)) as Numeric)
						)
					}
				}
			}

			is FunctionBrackets -> other - this

			else -> {
				other as Numeric
				if (degree == 0) {
					copy(number = (number + other) as Numeric)
				} else {
					FunctionBrackets().apply {
						addElements(this@PolynomialTerm, PolynomialTerm(other, 0))
					}
				}
			}
		}

	override fun times(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '*')

			is PolynomialTerm -> copy(number = (number * other.number) as Numeric, degree = degree + other.degree)

			is FunctionBrackets -> other * this

			else -> copy(number = (number * other) as Numeric)
		}

	override fun div(other: DataSet): DataSet =
		when (other) {
			is Matrix -> throw IllegalOperationException(this::class, Matrix::class, '/')

			is PolynomialTerm -> copy(number = (number / other.number) as Numeric, degree = degree - other.degree)

			is FunctionBrackets -> throw IllegalOperationException(this::class, FunctionBrackets::class, '/')

			else -> copy(number = (number / other) as Numeric)
		}

	override fun rem(other: DataSet) = throw IllegalOperationException(this::class, other::class, '%')

	//TODO для Complex
	override fun toString(): String = "$number * X^$degree"
}