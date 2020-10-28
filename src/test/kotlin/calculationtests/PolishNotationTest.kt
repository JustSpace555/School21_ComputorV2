package calculationtests

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.calcexception.DivideByZeroException
import models.exception.calcexception.IllegalTokenException
import models.exception.calcexception.TooFewOperatorsException
import models.math.dataset.numeric.SetNumber
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
	}
}