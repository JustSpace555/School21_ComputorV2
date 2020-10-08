package models.exception

import java.lang.Exception

class ParserException(override val message: String): Exception(message) {
	constructor(): this("")
	constructor(exm: ExceptionMessages): this(exm.message)
}