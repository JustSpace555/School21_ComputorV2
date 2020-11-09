package calculationtests.variable.function

import computorv1.models.PolynomialTerm
import models.math.FunctionBrackets
import org.junit.Assert.assertEquals
import org.junit.Test

class FunctionBracketsExpressionTest {

	@Test
	fun validPlusTest() {
		val expected = FunctionBrackets(mutableListOf(
			PolynomialTerm(1, 2), PolynomialTerm(-1, 1), PolynomialTerm(3, 0)
		))

		val firstFunction = FunctionBrackets(mutableListOf(
			PolynomialTerm(-1, 2), PolynomialTerm(-2, 1), PolynomialTerm(5.5, 0)
		))

		val secondFunction = FunctionBrackets(mutableListOf(
			PolynomialTerm(2, 2), PolynomialTerm(1, 1), PolynomialTerm(-2.5, 0)
		))

		assertEquals(expected, firstFunction + secondFunction)
	}
}