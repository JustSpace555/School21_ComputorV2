import models.exceptions.ComputorException
import models.tempVariables
import parser.extensions.addToHistory
import parser.extensions.getHistory
import parser.extensions.getVariablesList
import parser.parser
import java.util.*

fun main() {
	val scanner = Scanner(System.`in`)
	var input: String

	scanner.hasNext()
	while(scanner.hasNext()) {
		input = scanner.nextLine()

		var isPlot = false
		when {
			input.startsWith("exit") -> return
			input.startsWith("history") -> { println(getHistory()); continue }
			input.startsWith("variables") -> { println(getVariablesList()); continue }
			input.startsWith("plot") -> { isPlot = true }
		}

		try {
			parser(input, isPlot).also {
				addToHistory(input, it)
				println("$it\n")
			}
		} catch (e : ComputorException) {
			addToHistory(input, e.message)
			println(e.message + "\n")
		} catch (e: Exception) {
			addToHistory(input, e.message ?: "Exception")
			println("Something went wrong :(\n${e.message}\n")
			e.printStackTrace()
		} finally {
			tempVariables.clear()
		}
	}
}