package calculationtests.variable.function

import models.dataset.Function
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces

class FunctionSetNumberTest : FunctionExpressionTest("z", "z^2 + z + 1") {

	@Test
	fun validPlusSetNumberTest() {
		assertEquals(functionStr, (function + SetNumber()).toString())
		assertEquals("(($functionStr) + 1)", (function + SetNumber(1)).toString())
		assertEquals("(($functionStr) - 1)", (function + SetNumber(-1)).toString())
	}

	@Test
	fun validMinusSetNumberTest() {
		assertEquals(functionStr, (function - SetNumber()).toString())
		assertEquals("(($functionStr) - 1)", (function - SetNumber(1)).toString())
		assertEquals("(($functionStr) + 1)", (function - SetNumber(-1)).toString())
	}

	@Test
	fun validTimesSetNumberTest() {
		assertEquals("0", (function * SetNumber()).toString())
		assertEquals(functionStr, (function * SetNumber(1)).toString())
		assertEquals("($functionStr) * -1", (function * SetNumber(-1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber zero test`() {
		function / SetNumber()
	}

	@Test
	fun validDivSetNumberTest() {
		assertEquals("$functionStr", (function / SetNumber(1)).toString())
		assertEquals("(($functionStr) / (2))", (function / SetNumber(2)).toString())
		assertEquals("(($functionStr) / (-2.2))", (function / SetNumber(-2.2)).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem SetNumber test`() {
		function % SetNumber()
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test 1,0`() {
		function.pow(SetNumber(1.0))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test 2,2`() {
		function.pow(SetNumber(2.2))
	}

	@Test
	fun validPowTest() {
		assertEquals("1", function.pow(SetNumber()).toString())
		assertEquals(function, function.pow(SetNumber(1)))
		assertEquals("($functionStr) * ($functionStr)", function.pow(SetNumber(2)).toString())
	}

	@Test
	fun validInvokeTest() {
		assertEquals(SetNumber(3), function(1))
		assertEquals(SetNumber(3.31), function(1.1))

		function = Function(
			"abc",
			putSpaces("0.5 * abc - abc + abc ^ 2 - (abc + abc * 2)").split(' ').toTypedArray()
		)
		assertEquals(SetNumber(), function(0))
		assertEquals(SetNumber(-2.5), function(2.5))
	}
}