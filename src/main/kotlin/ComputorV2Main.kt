import models.dataset.DataSet
import parser.parser
import java.util.*

val scanner = Scanner(System.`in`)
val variables = mutableMapOf<String, DataSet>()

fun main() {
    var input = ""

    while (scanner.hasNext()) {
        input = scanner.nextLine()
        if (input == "exit")
            return
        parser(input)
    }
}