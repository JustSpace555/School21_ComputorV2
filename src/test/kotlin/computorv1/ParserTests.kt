import org.junit.Test
import parser.SignalCodes
import parser.parser
import java.lang.Exception
import java.util.*


class ParserTests {

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
		inputs.add(".1=0")
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
			val parserOutput = parser(element)
			if (parserOutput.third != SignalCodes.OK) {
				println("Test passed: $element")
				println("Fails with ${parserOutput.third}")
				println()
				continue
			}
			val message = "Test fail on: $element with ${parserOutput.third}\n" + parserOutput.first.toString()
			throw Exception(message)
		}
	}
}
