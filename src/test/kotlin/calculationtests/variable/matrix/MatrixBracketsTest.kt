package calculationtests.variable.matrix

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class MatrixBracketsTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Brackets test`() {
		matrix + middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Brackets test`() {
		matrix - middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Brackets test`() {
		matrix * middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Brackets test`() {
		matrix / middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Brackets test`() {
		matrix % middleBrackets
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Brackets test`() {
		matrix.pow(middleBrackets)
	}
}