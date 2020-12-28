package parser.variable.numeric

import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber

fun parseComplexFromList(input: List<String>): Complex {

	lateinit var re: SetNumber
	lateinit var im: SetNumber

	if (input.size == 1) return input.first().toComplex()

	if (input.last().isComplex()) {
		re = input.first().toSetNumber()
		im = input.last().toComplex().imaginary
	} else {
		re = input.last().toSetNumber()
		im = input.first().toComplex().imaginary
	}

	if (input[1] == "-")
		re *= -1

	return Complex(re, im)
}