package parser.variable

import computation.*
import computation.polishnotation.extensions.compute
import computorv1.models.PolynomialTerm
import globalextensions.toPolynomial
import globalextensions.toPolynomialList
import globalextensions.tryCastToInt
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.BracketsAmountException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import models.exceptions.computorv2.calcexception.variable.NoSuchVariableException
import models.tempVariables
import models.variables
import parser.variable.numeric.toNumeric

private fun Map<String, DataSet>.checkIsElementNumeric(name: String) {
	val element = this.getOrElse(name) { return }
	if (element !is Numeric) throw IllegalOperationException(Function::class, element::class)
}

fun parseFunctionFromList(input: List<String>, parameter: String): DataSet {
	val function = variables[input.first()] as Function?

	val indexOfOpenBracket = input.indexOf("(")
	val indexOfCloseBracket = input.lastIndexOf(")")
	if (indexOfOpenBracket == -1 || indexOfCloseBracket == -1) throw BracketsAmountException()

	val numberList = input.subList(indexOfOpenBracket + 1, indexOfCloseBracket)
	val result = if (numberList.size == 1) {
		val varName = numberList.first()
		when {
			variables.containsKey(varName) -> {
				variables.checkIsElementNumeric(varName)
				variables[varName]!!
			}
			tempVariables.containsKey(varName) -> {
				tempVariables.checkIsElementNumeric(varName)
				tempVariables[varName]!!
			}
			varName == parameter -> PolynomialTerm(1, 1, parameter)
			else -> varName.toNumeric()
		}
	} else {
		numberList.compute(parameter)
	}

	return when (result) {

		is Matrix -> when (input.first()) {
			"T" -> result.transposed()
			"DET" -> SetNumber(result.det())
			"REV" -> result.reverse()
			else -> throw IllegalOperationException(Function::class, Matrix::class, input.first())
		}

		is Numeric -> {

			val operation: ((Double) -> Double)? =
				when (input.first()) {
					"sin" -> ::sampleSin
					"cos" -> ::sampleCos
					"tan" -> ::sampleTan
					"ctg" -> ::sampleCtg
					"asin" -> ::sampleArcSin
					"acos" -> ::sampleArcCos
					"atan" -> ::sampleArcTan
					"actg" -> ::sampleArcCtg
					"exp" -> ::sampleExp
					"sqrt" -> ::sampleSqrt
					"abs" -> ::sampleAbs
					"fac" -> ::sampleFactorial
					else -> null
				}

			when {
				function == null && operation == null -> throw NoSuchVariableException(input.first())

				operation == null -> function!!(result)

				else -> {
					if (result is Complex) throw IllegalOperationException(Function::class, Complex::class)
					result as SetNumber
					SetNumber(operation(result.number.toDouble()).tryCastToInt())
				}
			}

		} else -> Function(
			parameter,
			function?.function?.map {
				PolynomialTerm(it.number, it.degree, parameter)
			} ?: listOf(result.toPolynomial()),
			input.first()
		).apply { addPolynomialsBeforeInvoke(result.toPolynomialList()) }
	}
}