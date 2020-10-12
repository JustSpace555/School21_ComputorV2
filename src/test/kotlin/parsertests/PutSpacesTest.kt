package parsertests

import org.junit.Assert.assertEquals
import org.junit.Test
import parser.putSpaces

class PutSpacesTest {

	@Test
	fun validTests() {
		assertEquals("varA = 2", putSpaces("varA = 2"))
		assertEquals("varA = 2 * i + 3", putSpaces("varA = 2*i + 3"))
		assertEquals("varA = -4i - 4", putSpaces("varA = -4i -4"))
		assertEquals("varA = [[2,3] ; [4,3]]", putSpaces("varA = [[2,3];[4,3]]"))
		assertEquals("funA ( x ) = 2 * x ^ 5 + 4x ^ 2 - 5 * x + 4", putSpaces("funA(x)=2*x^5+4x^2-5*x+4"))
		assertEquals("1 2", putSpaces("1              2"))
	}
}