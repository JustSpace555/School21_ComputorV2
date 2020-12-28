package parsertests

import ComputorTest
import models.exceptions.computorv1.EqualSignAmountException
import models.exceptions.computorv1.EqualSignPositionException
import models.exceptions.computorv2.parserexception.sign.QuestionMarkPositionException
import models.variables
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.parser

class ParserMainTest : ComputorTest() {

	@Test(expected = EqualSignAmountException::class)
	fun `fail test with multiple amount of equal signs`() {
		parser("a = 2 + 3 + 4 = 1")
	}

	@Test(expected = EqualSignPositionException::class)
	fun `fail test with equal sign on start`() {
		parser("= 1 + 2 + 3")
	}

	@Test(expected = EqualSignPositionException::class)
	fun `fail test with equal sign on finish`() {
		parser("1 + 2 + 3 =")
	}

	@Test(expected = QuestionMarkPositionException::class)
	fun `fail test with question mark on wrong position`() {
		parser("1 + 2 + ? = 3")
	}

	@Test
	fun validParserTest() {
		assertEquals("2.1", parser("varA = 2.1"))
		assertEquals("x^2 + x + 1", parser("funA(x)=(x^(2))+(x+1 + 1 -1)"))
		assertEquals("y + (funA(y))", parser("funB(y) = y + funA(y)"))
		assertEquals("0.5 * z - 3 + (funB(z))", parser("funC(z) = z * 0.5 - funA((1) + (1) - (1)) + funB ( z )"))
		assertEquals("[ 1, 2 ]\n[ 3 + 3i, -5.5 ]\n", parser("matA = [[1, 2]; [3 + 3i, -5.5]]"))

		assertEquals("[ 0, 0 ]\n[ 382.153846176 + 182.769230832i, -290.769230808 + 58.153846128i ]\n", parser(
			"((3 + 1) * (-1.5) ^ 2 * (1 + i) / funA(1 + 1i)) * funB(6 - 1) / funC(varA - 1.1) * (matA + [[-1, -2]; [1, 2]])"
		))
		variables.clear()
	}

	@Test
	fun validHardParserTest() {
		assertEquals("2.1", parser("varA = 2.1"))
		assertEquals("x^2 + x + 1", parser("funA(x)=(x^(2))+(x+1 + 1 -1)"))
		assertEquals("y + (funA(y))", parser("funB(y) = y + funA(y)"))
		assertEquals(
			"(funB((funA(z + 2)) + (funB(z + 2)))) * (funA(z)) * -z^2 + 0.5 * z - 3",
			parser("funC(z) = z * 0.5 - funA((1) + (1) - (1)) - funB ( funA(z + varA - 0.1) + funB(z + varA - 0.1) )*z^2*funA(z)")
		)
		assertEquals("-2702.5", parser("funC(1) = ?"))

		assertEquals(
			"0.5 * z - 3 + ((-z^2 * (funB((funA(z + 2)) + (funB(z + 2))))) / (funA(z)))",
			parser("funC(z) = z * 0.5 - funA((1) + (1) - (1)) - funB ( funA(z + varA - 0.1) + funB(z + varA - 0.1) )*z^2/funA(z)")
		)

		assertEquals("-302.5", parser("funC(1) = ?"))
	}
}