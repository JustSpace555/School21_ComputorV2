package calculationtests.variable.wrapping.functionstack

import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.FunctionStack
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class FunctionStackComplexTest : FunctionStackExpressionTest() {

	@Test
	fun validPlusComplexTest() {
		assertEquals("(x^2) * 0.1", (functionStack + Complex(0, 0)).toString())
		assertEquals("((2.2 + 3.3i) + 0.1 * (x^2))", (functionStack + complex).toString())
		assertEquals("((-2.2 - 3.3i) + 0.1 * (x^2))", (functionStack + complex * SetNumber(-1)).toString())
	}

	@Test
	fun validMinusComplexTest() {
		assertEquals("(x^2) * 0.1", (functionStack - Complex(0, 0)).toString())
		assertEquals("((-2.2 - 3.3i) + 0.1 * (x^2))", (functionStack - complex).toString())
		assertEquals("((2.2 + 3.3i) + 0.1 * (x^2))", (functionStack - complex * SetNumber(-1)).toString())
	}

	@Test
	fun validTimesComplexTest() {
		assertEquals("0", (functionStack * Complex(0, 0)).toString())
		assertEquals("0.1 * (x^2)", (functionStack * Complex(1, 0)).toString())
		assertEquals("(0.1 + 0.1i) * (x^2)", (functionStack * Complex(1, 1)).toString())
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber test`() {
		functionStack / Complex(0, 0)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Complex SetNumber test`() {
		assertEquals("(0.1 * (x^2)) / (-2.2 - 3.3i)", (functionStack / (complex * SetNumber(-1))).toString())
	}

	@Test
	fun validDivComplexTest() {
		assertEquals("($middleStr * (x^2)) / (2.2 + 3.3i)", (FunctionStack(middleBrackets, function) / complex).toString())
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Complex test`() {
		functionStack % complex
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Complex test`() {
		functionStack.pow(complex)
	}
}