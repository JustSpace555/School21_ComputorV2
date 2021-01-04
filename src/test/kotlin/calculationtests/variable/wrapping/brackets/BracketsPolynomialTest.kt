package calculationtests.variable.wrapping.brackets

import ComputorTest
import computorv1.models.PolynomialTerm
import models.dataset.DataSet
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import org.junit.Assert.assertEquals
import org.junit.Test

class BracketsPolynomialTest : ComputorTest() {

	@Test
	fun validPlusTest() {
		val expected = Brackets(listOf(
			PolynomialTerm(1, 3), PolynomialTerm(1, 2),
			PolynomialTerm(-1, 1), SetNumber(3)
		))

		val firstFunction = Brackets(listOf(
			PolynomialTerm(-1, 2), PolynomialTerm(-2, 1), SetNumber(5.5)
		))

		val secondFunction = Brackets(listOf(
			PolynomialTerm(1, 3), PolynomialTerm(2, 2),
			PolynomialTerm(1, 1), SetNumber(-2.5)
		))

		assertEquals(expected, firstFunction + secondFunction)
	}

	@Test
	fun validMinusTest() {
		val expected = Brackets(listOf(
			PolynomialTerm(Complex(2, -1), 2), PolynomialTerm(-1, 1), SetNumber(3)
		))

		val firstFunction = Brackets(listOf(
			PolynomialTerm(-1, 2), PolynomialTerm(-2, 1), SetNumber(5.5)
		))

		val secondFunction = Brackets(listOf(
			PolynomialTerm(Complex(-3, 1), 2), PolynomialTerm(-1, 1), SetNumber(2.5)
		))

		assertEquals(expected, firstFunction - secondFunction)
	}

	@Test
	fun validTimesTest() {
		var expected = Brackets(listOf(
			PolynomialTerm(1, 2), PolynomialTerm(10, 1), SetNumber(25)
		)) as DataSet

		var firstFunction = Brackets(listOf(
			PolynomialTerm(1, 1), SetNumber(5)
		))

		var secondFunction = firstFunction

		assertEquals(expected, firstFunction * secondFunction)



		expected = Brackets(listOf(
			PolynomialTerm(Complex(3, 1), 7), PolynomialTerm(Complex(9, 1), 5),
			PolynomialTerm(Complex(8, 6), 4), PolynomialTerm(8, 3),
			PolynomialTerm(Complex(18, 6), 2), PolynomialTerm(2, 1),
			Complex(6, 2)
		))

		firstFunction = Brackets(listOf(
			PolynomialTerm(Complex(3, 1), 4), PolynomialTerm(6, 2),
			SetNumber(2)
		))

		secondFunction = Brackets(listOf(
			PolynomialTerm(1, 3), PolynomialTerm(1, 1), Complex(3, 1)
		))

		assertEquals(expected, firstFunction * secondFunction)



		expected = Brackets (PolynomialTerm(5, 1), SetNumber(26),
			PolynomialTerm(5, -1)
		)

		firstFunction = Brackets(PolynomialTerm(1, 1), SetNumber(5))

		secondFunction = Brackets(PolynomialTerm(1, -1), SetNumber(5))

		assertEquals(expected, firstFunction * secondFunction)
	}

	@Test
	fun validDivTest() {
		val expected = Brackets(listOf(
			PolynomialTerm(1, 2), PolynomialTerm(10, 1), SetNumber(25)
		)) as DataSet

		val firstFunction = Brackets(listOf(
			PolynomialTerm(1, 2), PolynomialTerm(10, 1), SetNumber(25)
		))

		assertEquals(expected, firstFunction / SetNumber(1))
	}
}