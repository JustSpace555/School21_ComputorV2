package globalextensions

import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.FunctionStack
import models.dataset.wrapping.Wrapping

fun DataSet.getBracketList(): List<DataSet> = when(this) {
	!is Brackets -> listOf(this)
	else -> this.listOfOperands
}

fun DataSet.toPolynomial(): PolynomialTerm =
	when (this) {
		is Wrapping -> when {
			listOfOperands.any { it is PolynomialTerm } -> {
				val newList = listOfOperands.toMutableList()
				val maxPol = listOfOperands.filterIsInstance<PolynomialTerm>().maxByOrNull { it.degree }!!
				newList.remove(maxPol)
				val newVal = if (this is Brackets) Brackets(newList) else FunctionStack(newList)
				PolynomialTerm(newVal * maxPol.number, maxPol.degree, maxPol.name)
			}

			else -> PolynomialTerm(this)
		}

		is PolynomialTerm -> this

		else -> PolynomialTerm(this)
	}

fun List<DataSet>.mapToPolynomialList(): List<PolynomialTerm> = this.map { it.toPolynomial() }

