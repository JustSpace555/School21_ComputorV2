import parser.extensions.putSpaces

abstract class ComputorTest {
	protected fun String.getList() = putSpaces(this).split(' ').filter { it.isNotEmpty() }
}