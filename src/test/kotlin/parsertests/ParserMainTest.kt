package parsertests

import models.exceptions.computorv1.parserexception.EqualSignAmountException
import models.exceptions.computorv1.parserexception.EqualSignPositionException
import models.exceptions.computorv2.parserexception.sign.QuestionMarkPositionException
import org.junit.Test
import parser.parser

class ParserMainTest : ParserTest() {

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
}