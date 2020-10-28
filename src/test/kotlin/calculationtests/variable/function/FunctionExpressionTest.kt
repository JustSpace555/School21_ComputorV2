package calculationtests.variable.function

import models.math.dataset.Function
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces

class FunctionExpressionTest {

	@Test
	fun validTest() {
		val function: Function = Function("x", putSpaces("x^2 + x + 1").split(' '))

		assertEquals(SetNumber(3), function(1))
		assertEquals(SetNumber(3.31), function(1.1))
		assertEquals(Complex(2, 3), function(Complex(1, 1)))
	}
}