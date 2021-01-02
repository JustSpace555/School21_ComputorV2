package globalextensions

import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.Matrix
import models.dataset.numeric.Numeric
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.FunctionStack
import models.dataset.wrapping.Wrapping
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

fun DataSet.getBracketList(): List<DataSet> = when(this) {
	!is Brackets -> listOf(this)
	else -> this.listOfOperands
}

fun DataSet.toPolynomial(): PolynomialTerm =
	when (this) {

		is Matrix -> throw IllegalOperationException(PolynomialTerm::class, Matrix::class)

//		is Wrapping -> when {
//			listOfOperands.any { it is PolynomialTerm } -> {
//				val newList = listOfOperands.toMutableList()
//				val maxPol = listOfOperands.filterIsInstance<PolynomialTerm>().maxByOrNull { it.degree }!!
//				newList.remove(maxPol)
//				val newVal = if (this is Brackets) Brackets(newList) else FunctionStack(newList)
//				maxPol.copy(number = newVal * maxPol.number)
//			}
//
//			else -> PolynomialTerm(this)
//		}

		is PolynomialTerm -> this

		else -> PolynomialTerm(this)
	}

fun List<DataSet>.mapToPolynomialList(): List<PolynomialTerm> = this.map { it.toPolynomial() }

fun DataSet.isEmpty() =
	when(this) {
		is Wrapping -> isEmpty
		is Numeric -> isZero()
		is PolynomialTerm -> {
			when (number) {
				is Wrapping -> number.isEmpty
				is Numeric -> number.isZero()
				else -> false
			}
		}
		else -> false
	}

fun DataSet.isNotEmpty() = !isEmpty()

fun DataSet.toPolynomialList() =
	when(this) {
		is Wrapping -> listOfOperands.mapToPolynomialList()
		is PolynomialTerm -> listOf(this)
		else -> listOf(PolynomialTerm(this))
	}