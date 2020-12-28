package computation

import globalextensions.compareTo
import models.dataset.DataSet
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException

fun sampleMax(n1: Number, n2: Number): Number = if (n1 > n2) n1 else n2
fun sampleMin(n1: Number, n2: Number): Number = if (n1 < n2) n1 else n2

fun DataSet.sampleSqrt(degree: Double = 0.5): SetNumber {
	if (this !is SetNumber) throw IllegalOperationException(this::class, SetNumber::class, "sqrt")
	var lBorder = 0.0
	var rBorder = sampleMax(1, this.number).toDouble()
	var mid = SetNumber((lBorder + rBorder) / 2)
	val rightDegree = 1 / degree
	var i = 0

	while (mid.samplePow(rightDegree) != this && i < 500) {
		if (lBorder  == rBorder) break

		val p = mid.samplePow(rightDegree)
		when {
			p > this -> rBorder = mid.number.toDouble()
			p < this -> lBorder = mid.number.toDouble()
			else -> return mid
		}
		mid = SetNumber((lBorder + rBorder) / 2)
		i++
	}
	return mid
}

//TODO Добавить возможность возводить в дробные степени (2.6, 3.9123, ...)
fun DataSet.samplePow(degree: Double): SetNumber {
	if (this !is SetNumber) throw IllegalOperationException(this::class, SetNumber::class, "^")

	return when (degree) {
		0.0 -> SetNumber(1.0)
		in Double.MIN_VALUE..0.0 -> SetNumber(1) / samplePow(degree * -1)
		1.0 -> this
		in 0.0..1.0 -> sampleSqrt(degree)
		else -> {
			var i = -1
			var output = SetNumber(1.0)
			while (++i < degree) output *= this
			output
		}
	}
}