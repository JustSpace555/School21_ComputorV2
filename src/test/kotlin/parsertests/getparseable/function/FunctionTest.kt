package parsertests.getparseable.function

import models.dataset.Function
import models.exception.parserexception.variable.function.MultipleArgumentException
import models.exception.parserexception.variable.function.WrongFunctionBracketsFormatException
import org.junit.Assert
import org.junit.Test
import parser.getparseable.getParseableDataSet
import parser.putSpaces

class FunctionTest {

	private fun String.getList() = putSpaces(this).split(" ")

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

	@Test
	fun validFunctionTests() {
		Assert.assertEquals(Function::class, getParseableDataSet("fun(x)=5*x".getList()))
		Assert.assertEquals(Function::class, getParseableDataSet("fun((x))=5*x".getList()))
		Assert.assertEquals(Function::class, getParseableDataSet("abc((x))=5*x".getList()))
		Assert.assertEquals(Function::class, getParseableDataSet("a(((x)))=5*x".getList()))
	}
}