package calculationtests

import computation.extensions.simplifyBrackets
import org.junit.Test
import parser.extensions.putSpaces

class SimplifyBracketsTest {

	@Test
	fun validTest() {
		println(simplifyBrackets(putSpaces("x + x^2 - 3 + 2 - (((x + 2) + 3) ^ 2)").split(' '), "x")())
	}
}