package calculationtests.variable.matrix

import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class MatrixFractionTest : MatrixExpressionTest() {

	@Test(expected = IllegalOperationException::class)
	fun `fail plus Fraction test`() {
		matrix + fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail minus Fraction test`() {
		matrix - fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail times Fraction test`() {
		matrix * fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail div Fraction test`() {
		matrix / fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem Fraction test`() {
		matrix % fraction
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow Fraction test`() {
		matrix.pow(fraction)
	}
}