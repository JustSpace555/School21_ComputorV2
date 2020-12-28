package computorv1.parser.extensions

import models.exceptions.computorv1.PolynomialArgumentNameException
import models.exceptions.computorv1.PolynomialDegreeFormatException
import models.exceptions.computorv1.PolynomialNumberFormatException
import globalextensions.times
import globalextensions.tryCastToInt
import computorv1.models.PolynomialTerm
import java.lang.NumberFormatException

fun getConstNumber(input: String): Number {

	if (input.first().isLetter()) return 1

	val numberListStr =
		if (input.contains('*'))
			input.split('*').filter {
				!it.contains('^') && !it.contains('x') && !it.contains('X')
			}
		else
			listOf(input)

	var number: Number = 1

	numberListStr.forEach {
		if (it.contains('.')) {
			try { number *= it.toDouble() }
			catch (e: NumberFormatException) { throw PolynomialNumberFormatException(it) }
		} else {
			try { number *= it.toInt() }
			catch (e: NumberFormatException) { throw PolynomialNumberFormatException(it) }
		}
	}

	return number.tryCastToInt()
}

fun getDegree(input: String): Int {
	if (!input.contains('^')) {
		return when {
			input.last().isDigit() && input.contains(Regex("[xX]")) ->
				throw PolynomialDegreeFormatException(input)
			input.contains(Regex("[xX]")) -> 1
			else -> 0
		}
	}

	val degree = try {
		input.slice(input.indexOf('^') + 1 until input.length).toInt()
	} catch (e: NumberFormatException) {
		throw PolynomialDegreeFormatException(input)
	}

	return degree
}

fun toPolynomialTerm(input: String): PolynomialTerm {
	input.forEach {
		if (it.isLetter() && !(it == 'x' || it == 'X')) throw PolynomialArgumentNameException(it)
	}
	return PolynomialTerm(getConstNumber(input), getDegree(input))
}