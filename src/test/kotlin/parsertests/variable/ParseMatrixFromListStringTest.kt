package parsertests.variable

import ComputorTest
import models.dataset.numeric.Complex
import models.dataset.numeric.Numeric
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.parserexception.variable.EmptyMatrixArgumentException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces
import parser.variable.parseMatrixFromListString

class ParseMatrixFromListStringTest : ComputorTest() {

	private fun String.getMatrix() = parseMatrixFromListString(
		putSpaces(this).split(' ').toTypedArray()
	)

	@Test(expected = EmptyMatrixArgumentException::class)
	fun `fail test with not equal amount of elements in columns`() {
		"[[1, 2]; [1]]".getMatrix()
	}

	@Test
	fun validTests() {
		val elements: List<List<Numeric>> = listOf(
			listOf(SetNumber(1), SetNumber(2), SetNumber(-3)),
			listOf(SetNumber(4), Complex(1.1, 5.5), SetNumber(-6.6))
		)

		assertEquals(elements,"[[1, 2, -3] ; [4, 1.1 + 5.5i, -6.6]]".getMatrix())
		assertEquals(
			elements,
			"[[1 ^ 1, 3 + (-1), -3 ^ 1] ; [2 ^ 4 - 4 ^ 2 + 2 * 2, 6 - 5 + 0.1 + 10i - 5i + 0.5i, -6.6]]".getMatrix()
		)
	}
}