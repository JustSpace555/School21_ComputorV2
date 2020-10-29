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
	val withoutI = removeSuffix("i")
	if (withoutI.isEmpty()) return Complex(imaginary = 1)

	val parsed = withoutI.toDoubleOrNull()?.tryCastToInt()
		?: throw SetNumericFormatException(this, Complex::class)

	return Complex(imaginary = SetNumber(parsed))
}

fun String.toNumeric(): Numeric = if (isComplex()) toComplex() else toSetNumber()