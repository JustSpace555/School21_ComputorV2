package parsertests.configurateparseable

import models.math.dataset.numeric.SetNumber
import models.math.variables
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.configurateparseable.putSetNumber
import parsertests.ParserTest

class PutSetNumberTest : ParserTest() {

	@Test
	fun validTests() {
		putSetNumber("varA", listOf("1"))
		putSetNumber("varB", listOf("1.1"))
		putSetNumber("varC", "1.5 + 2 * 3 ^ ( 4 - 3 % 1 )".split(' '))

		assertEquals(SetNumber(1), variables["varA"])
		assertEquals(SetNumber(1.1), variables["varB"])
		assertEquals(SetNumber(7.5), variables["varC"])
	}
}