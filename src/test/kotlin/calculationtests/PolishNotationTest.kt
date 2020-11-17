package calculationtests

import computation.polishnotation.extensions.compute
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.IllegalTokenException
import models.exceptions.computorv2.calcexception.TooFewOperatorsException
import models.dataset.Matrix
import models.dataset.Function
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.tempVariables
import models.variables
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces


class PolishNotationTest {

	private fun calc(input: String) = putSpaces(input)
		.split(' ')
		.filter { it.isNotEmpty() }
		.compute()

	@Test(expected = DivideByZeroException::class)
	fun `fail cals test division by zero`() {
		calc("  1    /     0")
	}

	@Test(expected = IllegalTokenException::class)
	fun `fail calc test with token in wrong position`() {
		calc("  +   1    /2")
	}

	@Test(expected = TooFewOperatorsException::class)
	fun `fail calc test with no operator`() {
		calc("1    3i")
	}

	@Test
	fun validTests() {
		assertEquals(SetNumber(5), calc("2   +        3"))
		assertEquals(SetNumber(-10), calc("2*5-10*2"))
		assertEquals(SetNumber(0), calc("(2 + 3) + (5 - 10)"))
		assertEquals(SetNumber(11), calc("(2+2)*3-1*1"))

		assertEquals(Complex(1, 1), calc("2 + 3i - 1 - 3i + i"))
	}

	@Test
	fun allValidTest() {

		variables["varA"] = SetNumber(2.1)
		variables["funA"] = Function("x", "x ^ 2 + x + 1".split(' ').toTypedArray())
		variables["funB"] = Function("y", "y + funA ( y )".split(' ').toTypedArray())
		variables["funC"] = Function("z", "z * 0.5 - funA ( 1 ) + funB ( z )".split(' ').toTypedArray())
		variables["matA"] = Matrix(putSpaces("[[1, 2]; [3 + 3i, -5.5]]").split(' ').toTypedArray())

		val result = calc(
			"((3 + 1) * (-1.5) ^ 2 * (1 + i) / funA(1 + 1i)) * funB(6 - 1) / funC(varA - 1.1) * (matA + [[-1, -2]; [1, 2]])"
		)

		val rightAnswer = Matrix(
			putSpaces("[[0, 0]; [382.153846176 + 182.769230832i, -290.769230808 + 58.153846128i]]")
				.split(' ').toTypedArray()
		)

		assertEquals(rightAnswer, result)
	}

	@After
	fun afterAll() {
		tempVariables.clear()
	}
}