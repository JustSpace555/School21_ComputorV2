package calculationtests.variable.function

import ComputorTest
import models.dataset.function.Function
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.DivideByZeroException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces

class FunctionExpressionTest : ComputorTest() {

	var function: Function = Function("z", "z^2 + z + 1".getList().toTypedArray())

	@Test
	fun validTest() {
		assertEquals(SetNumber(3), function(1))
		assertEquals(SetNumber(3.31), function(1.1))
		assertEquals(Complex(2, 3), function(Complex(1, 1)))

		function = Function(
			"abc",
			putSpaces("0.5 * abc - abc + abc ^ 2 - (abc + abc * 2)").split(' ').toTypedArray()
		)
		assertEquals(SetNumber(), function(0))
		assertEquals(SetNumber(-2.5), function(2.5))
		assertEquals(Complex(-3.5, -1.5), function(Complex(1, 1)))
	}

	@Test
	fun validPlusSetNumberTest() {
		assertEquals("z^2 + z + 1", (function + SetNumber()).toString())
		assertEquals("((z^2 + z + 1) + 1)", (function + SetNumber(1)).toString())
		assertEquals("((z^2 + z + 1) - 1)", (function + SetNumber(-1)).toString())
	}

	@Test
	fun validMinusSetNumberTest() {
		assertEquals("z^2 + z + 1", (function - SetNumber()).toString())
		assertEquals("((z^2 + z + 1) - 1)", (function - SetNumber(1)).toString())
		assertEquals("((z^2 + z + 1) + 1)", (function - SetNumber(-1)).toString())
	}

	@Test
	fun validTimesSetNumberTest() {
		assertEquals("0", (function * SetNumber()).toString())
		assertEquals("z^2 + z + 1", (function * SetNumber(1)).toString())
		assertEquals("(z^2 + z + 1) * -1", (function * SetNumber(-1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber zero test`() {
		function / SetNumber()
	}

	@Test
	fun validDivSetNumberTest() {
		assertEquals("z^2 + z + 1", (function / SetNumber(1)).toString())
		assertEquals("((z^2 + z + 1) / (2))", (function / SetNumber(2)).toString())
		assertEquals("((z^2 + z + 1) / (-2.2))", (function / SetNumber(-2.2)).toString())
	}



	@Test
	fun validPlusComplexTest() {
		assertEquals("z^2 + z + 1", (function + Complex(0, 0)).toString())
		assertEquals("((z^2 + z + 1) + i)", (function + Complex(0, 1)).toString())
		assertEquals("((z^2 + z + 1) + (1 + i))", (function + Complex(1, 1)).toString())
	}

	@Test
	fun validMinusComplexTest() {
		assertEquals("z^2 + z + 1", (function - Complex(0, 0)).toString())
		assertEquals("((z^2 + z + 1) - i)", (function - Complex(0, 1)).toString())
		assertEquals("((z^2 + z + 1) + (-1 - i))", (function - Complex(1, 1)).toString())
	}

	@Test
	fun validTimesComplexest() {
		assertEquals("0", (function * SetNumber()).toString())
		assertEquals("z^2 + z + 1", (function * SetNumber(1)).toString())
		assertEquals("(z^2 + z + 1) * -1", (function * SetNumber(-1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Complex zero test`() {
		function / Complex(0, 0)
	}

	@Test
	fun validDivComplexTest() {
		assertEquals("z^2 + z + 1", (function / SetNumber(1)).toString())
		assertEquals("((z^2 + z + 1) / (2))", (function / SetNumber(2)).toString())
		assertEquals("((z^2 + z + 1) / (-2.2))", (function / SetNumber(-2.2)).toString())
	}



	@Test
	fun validPlusBracketsTest() {
		assertEquals("z^2 + z + 1", (function + Brackets()).toString())
		assertEquals("((z^2 + z + 1) + (z^2 + z + 1))", (function + Brackets(function)).toString())
		assertEquals("(1 + (z^2 + z + 1))", (function + Brackets(SetNumber(1))).toString())
	}

	@Test
	fun validMinusBracketsTest() {
		assertEquals("z^2 + z + 1", (function - Brackets()).toString())
		assertEquals("((z^2 + z + 1) - 1)", (function - Brackets(SetNumber(1))).toString())
		assertEquals("((z^2 + z + 1) + 1)", (function - Brackets(SetNumber(-1))).toString())
	}

	@Test
	fun validTimesBracketsTest() {
		assertEquals("0", (function * Brackets()).toString())
		assertEquals("z^2 + z + 1", (function * Brackets(SetNumber(1))).toString())
		assertEquals("(z^2 + z + 1) * -1", (function * Brackets(SetNumber(-1))).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div Brackets zero test`() {
		function / Brackets()
	}

	@Test
	fun validDivBracketsTest() {
		assertEquals("z^2 + z + 1", (function / Brackets(SetNumber(1)).reduceList()).toString())
		assertEquals("((z^2 + z + 1) / (2))", (function / Brackets(SetNumber(2)).reduceList()).toString())
	}



	@Test
	fun validPlusFunctionStackTest() {
		assertEquals("z^2 + z + 1", (function + FunctionStack()).toString())
		assertEquals("(1 + (z^2 + z + 1))", (function + FunctionStack(SetNumber(1))).toString())
		assertEquals("(-1 + (z^2 + z + 1))", (function + FunctionStack(SetNumber(-1))).toString())
	}

	@Test
	fun validMinusFunctionStackTest() {
		assertEquals("z^2 + z + 1", (function - FunctionStack()).toString())
		assertEquals("((z^2 + z + 1) - 1)", (function - FunctionStack(SetNumber(1))).toString())
		assertEquals("((z^2 + z + 1) + 1)", (function - FunctionStack(SetNumber(-1))).toString())
	}

	@Test
	fun validTimesFunctionStackTest() {
		assertEquals("0", (function * FunctionStack()).toString())
		assertEquals("z^2 + z + 1", (function * FunctionStack(SetNumber(1)).simplify()).toString())
		assertEquals("(z^2 + z + 1) * -1", (function * FunctionStack(SetNumber(-1)).simplify()).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div FunctionStack zero test`() {
		function / FunctionStack()
	}

	@Test
	fun validDivFunctionStackTest() {
		assertEquals("z^2 + z + 1", (function / FunctionStack(SetNumber(1)).simplify()).toString())
		assertEquals("((z^2 + z + 1) / (2))", (function / FunctionStack(SetNumber(2)).simplify()).toString())
	}



	@Test
	fun validPlusFunctionTest() {
		assertEquals("2 * (z^2 + z + 1)", (function + function).toString())
	}

	@Test
	fun validMinusFunctionTest() {
		assertEquals("0", (function - function).toString())
		assertEquals("((z^2 + z + 1) + (x^2 + x + 1) * -1)",
			(function - Function("x", "x^2 + x + 1".getList().toTypedArray())).toString()
		)
	}

	@Test
	fun validTimesFunctionTest() {
		assertEquals("(z^2 + z + 1) * (z^2 + z + 1)", (function * function).toString())
	}

	@Test
	fun validDivFunctionTest() {
		assertEquals("1", (function / function).toString())
		assertEquals("((z^2 + z + 1) / (x^2 + x + 1))",
			(function / Function("x", "x^2 + x + 1".getList().toTypedArray())).toString()
		)
	}
}