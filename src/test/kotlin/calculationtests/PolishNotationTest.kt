package calculationtests

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.calcexception.DivideByZeroException
import models.exception.calcexception.IllegalTokenException
import models.exception.calcexception.TooFewOperatorsException
import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import models.variables
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces


class PolishNotationTest {

	private fun calc(input: String) = calcPolishNotation(convertToPolishNotation(
		putSpaces(input).split(' ').filter { it.isNotEmpty() }
	))

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

		variables["funA"] = Function("x", "x ^ 2 + x + 1".split(' '))
		variables["funB"] = Function("y", "y + funA ( y )".split(' '))
		variables["funC"] = Function("z", "z * 0.5 - funA ( 1 ) + funB ( z )".split(' '))
		variables["matA"] = Matrix(putSpaces("[[1, 2]; [3 + 3i, -5.5]]").split(' ').toTypedArray())

		val result = calc(
			"((3 + 1) * (-1.5) ^ 2 * (1 + i) / funA(1 + 1i)) * funB(5) / funC(1) * (matA + [[-1, -2]; [1, 2]])"
		)

		val rightAnswer = Matrix(
			putSpaces("[[120.96 + 17.28i, 241.92 + 34.56i]; [-976.32 - 509.76i, -1952.64 - 1019.52i]]")
				.split(' ').toTypedArray()
		)

		assertEquals(rightAnswer, result)
	}
}