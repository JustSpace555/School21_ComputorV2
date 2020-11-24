package parser.variable.numeric

import globalextensions.tryCastToInt
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.parserexception.variable.SetNumericFormatException

fun String.isComplex() = contains(Regex("[0-9]*i"))

fun String.toSetNumber(): SetNumber {
	val parsed = toDoubleOrNull()?.tryCastToInt() ?: throw SetNumericFormatException(this, SetNumber::class)
	return SetNumber(parsed)
}

fun String.toComplex(): Complex {
	val withoutI = removeSuffix("i")
	if (withoutI.isEmpty()) return Complex(imaginary = 1)

	val parsed = withoutI.toDoubleOrNull()?.tryCastToInt()
		?: throw SetNumericFormatException(this, Complex::class)

	return Complex(imaginary = SetNumber(parsed))
}

fun String.toNumeric(): Numeric = if (isComplex()) toComplex() else toSetNumber()

fun String.isNumeric(): Boolean {
	if (isComplex()) return true
	try {
		this.toSetNumber()
	} catch (exc: SetNumericFormatException) {
		return false
	}
	return true
}