
import models.exception.ComputorException
import parser.parser
import java.util.*

fun main() {
	val scanner = Scanner(System.`in`)
	var input: String

	//TODO команды выхода, help, т.д.
	while (scanner.hasNext()) {
		input = scanner.nextLine()
		if (input == "exit") return

		lateinit var mathInstance: String
		try {
			mathInstance = parser(input)
		} catch (e : ComputorException) {
			println(e.message)
			continue
		}

//		//TODO подумать как переделать
//		if (mathInstance.second is Calculation) {
//			println((mathInstance.second as Calculation).invoke())
//			continue
//		}
//
//		variables[mathInstance.first] = mathInstance.second as DataSet
	}
}