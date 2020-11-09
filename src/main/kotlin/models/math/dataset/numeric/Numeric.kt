package models.math.dataset.numeric

import models.math.dataset.DataSet

interface Numeric : DataSet {

	fun isZero(): Boolean {
		if (this is SetNumber) return this.compareTo(0.0) == 0

		this as Complex
		return this.real.compareTo(0.0) == 0 && this.imaginary.compareTo(0.0) == 0
	}
}