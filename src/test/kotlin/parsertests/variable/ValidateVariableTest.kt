package parsertests.variable

import models.exceptions.computorv2.parserexception.variable.InvalidVariableFormatException
import models.math.dataset.Function
import models.math.dataset.Matrix
import models.math.dataset.numeric.Complex
import models.math.dataset.numeric.SetNumber
import org.junit.Test
import parser.extensions.validateVariable
import parsertests.ParserTest

class ValidateVariableTest : ParserTest() {

	@Test(expected = InvalidVariableFormatException::class)
	fun `fail test with wrong before equal size for SetNumber`() {
		validateVariable(listOf("a", "+", "b"), SetNumber::class)
	}

	@Test(expected = InvalidVariableFormatException::class)
	fun `fail test with wrong before equal size for Complex`() {
		validateVariable(listOf("a", "+", "b"), Complex::class)
	}

	@Test(expected = InvalidVariableFormatException::class)
	fun `fail test with wrong before equal size for Matrix`() {
		validateVariable(listOf("a", "+", "b"), Matrix::class)
	}

	@Test(expected = InvalidVariableFormatException::class)
	fun `fail test with wrong before equal size for Function with one element`() {
		validateVariable(listOf("a"), Function::class)
	}

	@Test(expected = InvalidVariableFormatException::class)
	fun `fail test with wrong before equal size for Function with more than four elements`() {
		validateVariable(listOf("funA", "(", "a", "+", "b", ")"), SetNumber::class)
	}
}