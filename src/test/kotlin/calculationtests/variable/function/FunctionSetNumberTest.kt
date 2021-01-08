package calculationtests.variable.function

import assertEquals
import models.dataset.Function
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test
import parser.extensions.putSpaces

class FunctionSetNumberTest : FunctionExpressionTest("z", "z^2 + z + 1") {

	@Test
	fun validPlusSetNumberTest() {
		assertEquals(functionStr, (newFunction + SetNumber()).toString())
		assertEquals("(1 + ($functionStr))", (newFunction + SetNumber(1)).toString())
		assertEquals("(-1 + ($functionStr))", (newFunction + SetNumber(-1)).toString())
	}

	@Test
	fun validMinusSetNumberTest() {
		assertEquals(functionStr, (newFunction - SetNumber()).toString())
		assertEquals("(-1 + ($functionStr))", (newFunction - SetNumber(1)).toString())
		assertEquals("(1 + ($functionStr))", (newFunction - SetNumber(-1)).toString())
	}

	@Test
	fun validTimesSetNumberTest() {
		assertEquals("0", (newFunction * SetNumber()).toString())
		assertEquals(functionStr, (newFunction * SetNumber(1)).toString())
		assertEquals("($functionStr) * -1", (newFunction * SetNumber(-1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber zero test`() {
		newFunction / SetNumber()
	}

	@Test
	fun validDivSetNumberTest() {
		assertEquals(functionStr, (newFunction / SetNumber(1)).toString())
		assertEquals("($functionStr) / 2", (newFunction / SetNumber(2)).toString())
		assertEquals("($functionStr) / -2.2", (newFunction / SetNumber(-2.2)).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem SetNumber test`() {
		newFunction % SetNumber()
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test 1,0`() {
		newFunction.pow(SetNumber(1.0))
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test 2,2`() {
		newFunction.pow(SetNumber(2.2))
	}

	@Test
	fun validPowTest() {
		assertEquals("1", newFunction.pow(SetNumber()).toString())
		assertEquals(newFunction, newFunction.pow(SetNumber(1)))
		assertEquals("($functionStr) * ($functionStr)", newFunction.pow(SetNumber(2)).toString())
	}

	@Test
	fun validInvokeTest() {
		assertEquals(SetNumber(3), newFunction(1))
		assertEquals(SetNumber(3.31), newFunction(1.1))

		newFunction = Function(
			"abc",
			putSpaces("0.5 * abc - abc + abc ^ 2 - (abc + abc * 2)").split(' ').toTypedArray()
		)
		assertEquals(SetNumber(), newFunction(0))
		assertEquals(SetNumber(-2.5), newFunction(2.5))
	}
}