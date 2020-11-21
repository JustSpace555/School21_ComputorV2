package computorv1

import ComputorTest
import computorv1.parser.parser
import models.exceptions.ComputorException
import org.junit.Test
import java.util.*

class ParserTests : ComputorTest() {

	@Test
	fun testInvalidEquations() {
		val inputs: MutableList<String>
		inputs = ArrayList()
		inputs.add("a")
		inputs.add("hello")
		inputs.add(".")
		testInvalidInputs(inputs)
	}

	@Test
	fun testInvalidCoefficient() {
		val inputs: MutableList<String>
		inputs = ArrayList()
		inputs.add(".2x^1=0")
		inputs.add("a2x^1=0")
		inputs.add("a1=0")
		inputs.add("111111111111111111111111111*x=0")
		testInvalidInputs(inputs)
	}

	@Test
	fun testInvalidVariable() {
		val inputs: MutableList<String>
		inputs = ArrayList()
		inputs.add("2**x^1")
		inputs.add("2*x^=0")
		inputs.add("2*x^1111111111111111111111111111=0")
		inputs.add("2*x^1.2=0")
		inputs.add("2*x^-1=0")
		inputs.add("2*x^^=0")
		inputs.add("2*y^2=0")
		testInvalidInputs(inputs)
	}

	@Test
	fun testInvalidEqualsSign() {
		val inputs: MutableList<String>
		inputs = ArrayList()
		inputs.add("==")
		inputs.add("2*x^1=0=0")
		inputs.add("2*x^1==")
		inputs.add("0=0=2*x^1")
		inputs.add("0==2*x^1")
		inputs.add("2*x^1=2*x^1=2*x^1")
		inputs.add("2*x^1==2*x^1")
		inputs.add("==2*x^1")
		testInvalidInputs(inputs)
	}

	private fun testInvalidInputs(input: List<String>) {
		for (element in input) {
			val parserOutput = try {
				parser(element, true)
			} catch (e: ComputorException) {
				continue
			}
			val message = "Test fail on: $element with and output: $parserOutput"
			throw Exception(message)
		}
	}
}
