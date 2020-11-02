package models.exception.parserexception.sign

abstract class EqualSignException: SignException()

class EqualSignPositionException: EqualSignException() {
	override val message: String = "Wrong equal sign position"
}
class EqualSignAmountException: EqualSignException() {
	override val message: String = "Wrong amount of equal signs"
}