package parsertests.getparseable

import models.exception.parserexception.variable.MultipleArgumentException
import models.exception.parserexception.variable.WrongFunctionBracketsFormatException
import models.math.dataset.Function
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.getparseable.getParseableDataSet
import parsertests.ParserTest

class GetParseableFunctionTest : ParserTest() {

	@Test(expected = WrongFunctionBracketsFormatException::class)
	fun `fail function test without open bracket`() {
		getParseableDataSet("abcx) = 5 * x".getList())
	}

	@Test(expected = WrongFunctionBracketsFormatException::class)
	fun `fail function test without close bracket`() {
		getParseableDataSet("abc(x = 5 * x".getList())
	}

	@Test(expected = WrongFunctionBracketsFormatException::class)
	fun `fail function test with multiple brackets`() {
		getParseableDataSet("abc(x)()=5 * x".getList())
	}

	@Test(expected = WrongFunctionBracketsFormatException::class)
	fun `fail function test with not equal amount brackets`() {
		getParseableDataSet("abc((x)=5 * x".getList())
	}

	@Test(expected = MultipleArgumentException::class)
	fun `fail function test with multiple arguments`() {
		getParseableDataSet("abc(x, y) = 5 * x".getList())
	}

	@Test(expected = MultipleArgumentException::class)
	fun `fail function test with multiple arguments in brackets`() {
		getParseableDataSet("abc(x)(y) = 5 * x".getList())
	}

	@Test(expected = MultipleArgumentException::class)
	fun `fail function test with multiple arguments one without brackets`() {
		getParseableDataSet("abc(x)y = 5 * x".getList())
	}

	@Test
	fun validFunctionTests() {
		val tests = listOf(
			"fun(x)=5*x",
			"fun((x))=5*x",
			"abc((y))=5*y",
			"a(((y)))=5*y"
		)

		tests.forEach {
			assertEquals(Function::class, getParseableDataSet(it.getList()))
		}
	}
}