package calculationtests.variable.wrapping.functionstack

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class FunctionStackMatrixTest : FunctionStackExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Matrix test`() {
		functionStack + matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Matrix test`() {
		functionStack - matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Matrix test`() {
		functionStack * matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Matrix test`() {
		functionStack / matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Matrix test`() {
		functionStack % matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Matrix test`() {
		functionStack.pow(matrix)
	}
}