package models.math.dataset.numeric

import globalextensions.compareTo
import globalextensions.div
import globalextensions.times

class Complex(private val re: Number, private val im: Number): Numeric {
	override fun toString(): String {
		if (re.toDouble() == 0.0)
			return "${im}i"

		var tempIm = im
		val signString = if (im < 0) {
			tempIm *= -1
			" - "
		} else {
			" + "
		}
		return "$re$signString${tempIm}i"
	}

	operator fun div(input: Number): Complex = Complex(this.re / input, this.im / input)
}