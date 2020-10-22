package parsertests.variable

import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.Numeric
import models.math.dataset.numeric.SetNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces
import parser.variable.parseMatrixFromListString

class ParseMatrixFromListStringTest {

	@Test
	fun validTests() {
		val elements: List<List<Numeric>> = listOf(
			listOf(SetNumber(1), SetNumber(2), SetNumber(-3)),
			listOf(SetNumber(4), Complex(imaginary = SetNumber(5)), SetNumber(-6.6))
		)

		assertEquals(elements, parseMatrixFromListString(
			putSpaces("[[1, 2, -3] ; [4, 5i, -6.6]]").split(' ').toTypedArray()
		))
	}
}