import parser.parser
import java.util.*

val scanner = Scanner(System.`in`)
val variables = mutableMapOf<String, Any>()

fun main() {
    var input = ""

    while (scanner.hasNext()) {
        input = scanner.nextLine()
        if (input == "exit")
            return
        parser(input)
    }
}