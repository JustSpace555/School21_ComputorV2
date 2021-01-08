package calculationtests.variable.matrix

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class MatrixPolynomialTermTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus PolynomialTerm test`() {
		matrix + pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus PolynomialTerm test`() {
		matrix - pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times PolynomialTerm test`() {
		matrix * pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div PolynomialTerm test`() {
		matrix / pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem PolynomialTerm test`() {
		matrix % pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow PolynomialTerm test`() {
		matrix.pow(pt)
	}
}