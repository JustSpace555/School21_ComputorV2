package parsertests.getparseable

import models.dataset.numeric.Complex
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.getparseable.getParseableDataSet
import parsertests.ParserTest

class GetParseableComplexTest : ParserTest() {

	@Test
	fun validTests() {
		val listOfTests = listOf (
			"varA=3i",
			"varA=3i+5",
			"varA=5 + 3123i",
			"varA=3123i+5*7-4123i"
		)

		listOfTests.forEach {
			assertEquals(Complex::class, getParseableDataSet(it.getList()))
		}
	}
}