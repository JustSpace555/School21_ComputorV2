package models.exceptions

abstract class ComputorException : RuntimeException() {
	abstract override val message: String?
}