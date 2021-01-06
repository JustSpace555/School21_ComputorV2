package calculationtests.variable.matrix.oldtests

import calculationtests.variable.matrix.MatrixExpressionTest
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class MatrixFunctionStackStackTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus FunctionStack test`() {
		matrix + functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus FunctionStack test`() {
		matrix - functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times FunctionStack test`() {
		matrix * functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div FunctionStack test`() {
		matrix / functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem FunctionStack test`() {
		matrix % functionStack
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow FunctionStack test`() {
		matrix.pow(functionStack)
	}
}