import parser.extensions.deleteSpacesFromMultipleOperations
import parser.extensions.putSpaces

abstract class ComputorTest {
	protected fun String.getList() = putSpaces(this)
		.split(' ')
		.deleteSpacesFromMultipleOperations()
		.filter { it.isNotEmpty() }
}