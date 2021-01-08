package calculationtests.variable.wrapping.fraction

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class FractionMatrixTest : FractionExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Matrix test`() {
		fraction + matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Matrix test`() {
		fraction - matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Matrix test`() {
		fraction * matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Matrix test`() {
		fraction / matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Matrix test`() {
		fraction % matrix
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Matrix test`() {
		fraction.pow(matrix)
	}
}