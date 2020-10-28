package parser.variable.numeric

import globalextensions.tryCastToInt
import models.exception.parserexception.variable.SetNumericFormatException
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber

fun String.isComplex() = contains(Regex("\\d*i"))

fun String.toSetNumber(): SetNumber {
	val parsed = toDoubleOrNull()?.tryCastToInt() ?: throw SetNumericFormatException(this, SetNumber::class)
	return SetNumber(parsed)
}

fun String.toComplex(): Complex {
	val parsed = removeSuffix("i").toDoubleOrNull()?.tryCastToInt()
		?: throw SetNumericFormatException(this, Complex::class)

	return Complex(imaginary = SetNumber(parsed))
}

fun String.toNumeric(): Numeric = if (isComplex()) toComplex() else toSetNumber()