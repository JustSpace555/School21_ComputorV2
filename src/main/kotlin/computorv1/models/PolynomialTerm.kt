package computorv1.models

import globalextensions.plus

data class PolynomialTerm(var number: Number, var degree: Int) {
	override fun toString(): String = "$number * X^$degree"

	operator fun plus(input: PolynomialTerm): PolynomialTerm = this.copy(number = number + input.number)
}