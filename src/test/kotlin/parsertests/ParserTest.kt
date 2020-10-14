package parsertests

import parser.extensions.putSpaces

abstract class ParserTest {
	protected fun String.getList() = putSpaces(this).split(' ').filter { it.isNotEmpty() }
}