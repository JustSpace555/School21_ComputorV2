
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

	while (scanner.hasNext()) {
		input = scanner.nextLine()

		when {
			input.startsWith("exit") -> return
			input.startsWith("history") -> { println(getHistory()); continue }
			input.startsWith("variables") -> { println(getVariablesList()); continue }
		}

		try {
			parser(input).also {
				addToHistory(input, it)
				println("$it\n")
			}
		} catch (e : ComputorException) {
			println(e.message)
		} catch (e: Exception) {
			println("Something went wrong :(\n${e.message}\n${e.stackTrace}")
		} finally {
			tempVariables.clear()
		}
	}
}