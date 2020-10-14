package calculationtests

import computation.polishnotation.calcPolishNotation
import computation.polishnotation.convertToPolishNotation
import models.exception.calcexception.DivideByZeroException
import models.exception.calcexception.IllegalTokenException
import models.exception.calcexception.TooFewOperatorsException
import models.math.dataset.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces


class PolishNotationTest {

	private fun calc(input: String) = calcPolishNotation(convertToPolishNotation(
		putSpaces(input).split(' ').filter { it.isNotEmpty() }
	))

	@Test(expected = DivideByZeroException::class)
	fun testShouldThrowExceptionOnDivByZero() {
		calc("  1    /     0")
	}

	@Test(expected = IllegalTokenException::class)
	fun testShouldThrowExceptionIllegalTokenException() {
		calc("  +   1    /2")
		calc("1    a")
	}

	@Test(expected = TooFewOperatorsException::class)
	fun testShouldThrowExceptionTooFewOperators() {
		calc("1      2")
	}

	@Test
	fun validTests() {
		assertEquals(SetNumber(5), calc("2   +        3"))
		assertEquals(SetNumber(-10), calc("2*5-10*2"))
		assertEquals(SetNumber(0), calc("(2 + 3) + (5 - 10)"))
		assertEquals(SetNumber(11), calc("(2+2)*3-1*1"))
	}
}