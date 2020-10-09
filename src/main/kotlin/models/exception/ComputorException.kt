package models.exception

import java.lang.RuntimeException

abstract class ComputorException: RuntimeException() {
	abstract override val message: String
}