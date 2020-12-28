package parsertests.getparseable

import ComputorTest
import models.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.getparseable.getParseableDataSet

open class GetParseableSetNumberTest : ComputorTest() {

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