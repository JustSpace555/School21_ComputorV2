package parser.variable.numeric

import globalextensions.tryCastToInt
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber

fun String.isComplex() = contains('i')

fun String.toSetNumber(): SetNumber {
	val parsed = toDoubleOrNull()?.tryCastToInt() ?: throw NumberFormatException()
	return SetNumber(parsed)
}

fun String.toComplex(): Complex {
	val parsed = removeSuffix("i").toDoubleOrNull()?.tryCastToInt() ?: throw NumberFormatException()
	return Complex(imaginary = SetNumber(parsed))
}

fun String.toNumeric(): Numeric = if (isComplex()) toComplex() else toSetNumber()