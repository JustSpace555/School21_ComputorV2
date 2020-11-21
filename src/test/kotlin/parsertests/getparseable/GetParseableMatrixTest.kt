package parsertests.getparseable

import ComputorTest
import models.dataset.Matrix
import models.exceptions.computorv2.parserexception.variable.EmptyMatrixArgumentException
import models.exceptions.computorv2.parserexception.variable.WrongMatrixBracketsFormatException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.getparseable.getParseableDataSet

class GetParseableMatrixTest : ComputorTest() {

	@Test(expected = WrongMatrixBracketsFormatException::class)
	fun `fail test without brackets`() {
		getParseableDataSet("vala = [1,2];[3,4] ** [1,2];[3,4] * 2".getList())
	}

	@Test(expected = WrongMatrixBracketsFormatException::class)
	fun `fail test without open bracket`() {
		getParseableDataSet("vala = [1,2];[3,4]] ** [[1,2];[3,4]] * 2".getList())
	}

	@Test(expected = WrongMatrixBracketsFormatException::class)
	fun `fail test without close bracket`() {
		getParseableDataSet("vala = [[1,2];[3,4] ** [[1,2];[1,2]]".getList())
	}

	@Test(expected = EmptyMatrixArgumentException::class)
	fun `fail test without first argument`() {
		getParseableDataSet("vala = [[,2,3];[4,5,6]]".getList())
	}

	@Test(expected = EmptyMatrixArgumentException::class)
	fun `fail test without middle argument`() {
		getParseableDataSet("vala = [[1,,3];[4,5,6]]".getList())
	}

	@Test(expected = EmptyMatrixArgumentException::class)
	fun `fail test without last argument`() {
		getParseableDataSet("vala = [[1,2,];[4,5,6]]".getList())
	}

	@Test
	fun validTests() {
		val listOfTests = listOf(
			"vala = [[1,2]]",
			"valA = [[1,-2];[3,4]]",
			"vala = [[i+1,2.9];[3,4]] ** [[1,2i];[3,4]] * 2 ^ 3i"
		)

		listOfTests.forEach {
			assertEquals(Matrix::class, getParseableDataSet(it.getList()))
		}
	}
}