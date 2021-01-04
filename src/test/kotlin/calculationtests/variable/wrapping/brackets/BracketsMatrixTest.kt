package calculationtests.variable.wrapping.brackets

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class BracketsMatrixTest : BracketsExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Matrix test`() {
		fullBrackets + matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Matrix test`() {
		fullBrackets - matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Matrix test`() {
		fullBrackets * matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Matrix test`() {
		fullBrackets / matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Matrix test`() {
		fullBrackets % matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Matrix test`() {
		fullBrackets.pow(matrix)
	}
}