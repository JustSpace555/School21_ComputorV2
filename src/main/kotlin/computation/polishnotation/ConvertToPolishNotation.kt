package computation.polishnotation

import computation.polishnotation.extensions.getOperandLastIndex
import computation.polishnotation.extensions.isComplexOrMatrixOrFunctionOrParameter
import computation.polishnotation.extensions.isOperandOrTempVariable
import computorv1.models.PolynomialTerm
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.exceptions.computorv2.calcexception.BracketsAmountException
import models.exceptions.computorv2.calcexception.IllegalTokenException
import models.exceptions.computorv2.calcexception.variable.NoSuchVariableException
import models.putTempVariable
import parser.variable.numeric.parseComplexFromList
import parser.variable.parseFunctionFromList
import java.util.*

private fun choosePriority(input: String): Int =
	when (input) {
		"^" -> 4
		"*", "/", "%" -> 3
		"+", "-" -> 2
		"(" -> 1
		else -> throw IllegalTokenException(input)
	}

fun convertToPolishNotation(input: List<String>, parameter: String = ""): List<String> {
	val output = mutableListOf<String>()
	val stack = Stack<String>()

	var i = 0
	while (i in input.indices) {

		val checkingOperand = input[i].isComplexOrMatrixOrFunctionOrParameter(parameter)
		if (checkingOperand.first) {
			val lastIndexToSlice = input.subList(i, input.size).getOperandLastIndex(checkingOperand.second)
			val variableList = input.subList(i, i + lastIndexToSlice)

			val tempVarName = putTempVariable(
				when(checkingOperand.second) {
					Complex::class -> parseComplexFromList(variableList)
					Function::class -> parseFunctionFromList(variableList, parameter)
					PolynomialTerm::class -> {
						PolynomialTerm(1, 1, if (parameter.isNotEmpty()) parameter else "X")
					}
					else -> Matrix(variableList.toTypedArray())
				}
			)

			output.add(tempVarName)
			i += lastIndexToSlice
			continue
		}

		if (input[i].isOperandOrTempVariable()) {
			output.add(input[i++])
			continue
		} else if (!input[i].contains(Regex("[\\^*/%+\\-()]"))) {
			throw NoSuchVariableException(input[i])
		}

		if (stack.isEmpty()) {
			stack.push(input[i++])
			continue
		}

		if (input[i] == ")") {
			while (stack.isNotEmpty() && stack.peek() != "(") output.add(stack.pop())

			if (stack.isEmpty()) throw BracketsAmountException()

			stack.pop()
			i++
			continue
		} else {
			val elementPriority = choosePriority(input[i])
			while (stack.isNotEmpty() && choosePriority(stack.peek()) >= elementPriority && elementPriority > 1)
				output.add(stack.pop())

			stack.push(input[i++])
		}
	}

	while (stack.isNotEmpty()) output.add(stack.pop())

	return output
}