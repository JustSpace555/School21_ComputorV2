package calculationtests.variable.wrapping.brackets

import computorv1.models.PolynomialTerm
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class BracketsPolynomialTermTest : BracketsExpressionTest() {

	@Test
	fun validPlusPolynomialTermTest() {
		assertEquals(
				"(2 * X^4 + (x^2) * y^3 + 0.1 + (x^2))",
				(middleBrackets + PolynomialTerm(2, 4)).toString()
		)

		assertEquals(
				"((x^2) * y^3 + 2 * X^2 + 0.1 + (x^2))",
				(middleBrackets + PolynomialTerm(2, 2)).toString()
		)

//		assertEquals(
//				"((x^2) * 3 * y^3 + 0.1 + (x^2))",
//				(middleBrackets + pt + pt).toString()
//		)
	}

	@Test
	fun validMinusPolynomialTermTest() {

		assertEquals(
				"(-2 * X^4 + (x^2) * y^3 + 0.1 + (x^2))",
				(middleBrackets - PolynomialTerm(2, 4)).toString()
		)

		assertEquals(
				"((x^2) * y^3 - 2 * X^2 + 0.1 + (x^2))",
				(middleBrackets - PolynomialTerm(2, 2)).toString()
		)

//		assertEquals(
//				"(0.1 + (x^2))",
//				(middleBrackets - pt).toString()
//		)
	}

	@Test
	fun validTimesPolynomialTermTest() {
		assertEquals(
				"0",
				(middleBrackets * PolynomialTerm(degree = 4)).toString()
		)

		assertEquals(
				"((x^2) * (x^2) * y^6 + (x^2) * 0.1 * y^3 + (x^2) * y^3 * (x^2))",
				(middleBrackets * pt).toString()
		)

		assertEquals(
				"(0.2 * X^4 + (x^2) * y^3 * 2 * X^4 + 2 * X^4 * (x^2))",
				(middleBrackets * PolynomialTerm(2, 4)).toString()
		)
	}

	@Test(expected = DivideByZeroException::class)
	fun `fail div SetNumber test`() {
		fullBrackets / PolynomialTerm(degree = 4)
	}

	@Test
	fun validDivPolynomialTest() {
//		assertEquals(generalStr, (fullBrackets / PolynomialTerm(1, 0)).toString())

		assertEquals(
				"(1 + X^-4)",
				(Brackets(SetNumber(1) + PolynomialTerm(1, 4)) / PolynomialTerm(1, 4)).toString()
		)

//		assertEquals(
//				"((x^2) * y^3 + 0.1 + (x^2)) / (X^4)",
//				(middleBrackets / PolynomialTerm(1, 4)).toString()
//		)
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail rem SetNumber test`() {
		fullBrackets % pt
	}

	@Test(expected = IllegalOperationException::class)
	fun `fail pow SetNumber test`() {
		fullBrackets.pow(pt)
	}
}