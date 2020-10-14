import models.math.dataset.DataSet
import parser.parser
import java.util.*

val variables = mutableMapOf<String, DataSet>()

fun main() {
    val scanner = Scanner(System.`in`)
    var input = ""

    while (scanner.hasNext()) {
        input = scanner.nextLine()
        if (input == "exit")
            return
        parser(input)
    }
}