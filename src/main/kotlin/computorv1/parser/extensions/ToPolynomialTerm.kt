package computorv1.parser.extensions

import computorv1.models.PolynomialTerm
import globalextensions.times
import models.exceptions.computorv1.parserexception.WrongArgumentNameException
import models.exceptions.computorv1.parserexception.WrongDegreeFormatException
import models.exceptions.computorv1.parserexception.WrongNumberFormatException
import models.dataset.numeric.SetNumber

internal fun String.containsX() = contains('x') || contains('X')

internal fun String.getConstNumber(): Number {

	if (first().isLetter())
		return 1

	val numberListStr =
		if (contains('*'))
			split('*').filter {
				!it.contains('^') && !it.contains('x') && !it.contains('X') }
		else
			listOf(this)

	var number: Number = 1

	numberListStr.forEach {
		try {
			number *= if (it.contains('.')) it.toDouble() else it.toInt()
		} catch (e: NumberFormatException) {
			throw WrongNumberFormatException()
		}
	}

	if (number.toDouble() - number.toInt() == 0.0)
		number = number.toInt()

	return number
}

internal fun String.getDegree(): Int {
	if (!contains('^')) {
		return if (last().isDigit() && containsX()) throw WrongDegreeFormatException(this)
		else if (containsX()) 1 else 0
	}

	return try {
		slice(indexOf('^') + 1 until length).toInt()
	} catch (e: NumberFormatException) {
		throw WrongDegreeFormatException(this)
	}.also { if (it < 0) throw WrongDegreeFormatException(it.toString()) }
}

internal fun String.toPolynomialTerm() : PolynomialTerm {
	forEach {
		if (it.isLetter() && !(it == 'x' || it == 'X')) throw WrongArgumentNameException(this)
	}
	return PolynomialTerm(SetNumber(getConstNumber()), getDegree())
}