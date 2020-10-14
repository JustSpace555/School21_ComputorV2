package parsertests.getparseable

import models.math.dataset.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.getparseable.getParseableDataSet
import parsertests.ParserTest

open class GetParseableSetNumberTest : ParserTest() {

	@Test
	fun validSetNumberTests() {
		val tests = listOf(
			"vara=5*3.1-4^3%1*(4-3)"
		)

		tests.forEach {
			assertEquals(SetNumber::class, getParseableDataSet(it.getList()))
		}
	}
}