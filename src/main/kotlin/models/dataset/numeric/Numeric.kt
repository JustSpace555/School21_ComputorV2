package models.dataset.numeric

import globalextensions.isZero
import models.dataset.DataSet

interface Numeric : DataSet {

	fun isZero(): Boolean {
		if (this is SetNumber) return number.isZero()

		this as Complex
		return real.number.isZero() && imaginary.number.isZero()
	}

	fun isNotZero(): Boolean = !isZero()
}