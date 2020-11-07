package computorv1.models

import globalextensions.plus
import globalextensions.times

data class PolynomialTerm(var number: Number, var degree: Int) {
	override fun toString(): String = "$number * X^$degree"

	operator fun plus(input: PolynomialTerm): PolynomialTerm = this.copy(number = number + input.number)
	operator fun times(input: PolynomialTerm): PolynomialTerm =
		this.copy(number = number * input.number, degree = degree + input.degree)
}