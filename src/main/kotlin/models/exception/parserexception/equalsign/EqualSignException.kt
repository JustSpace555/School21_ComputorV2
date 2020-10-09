package models.exception.parserexception.equalsign

import models.exception.parserexception.ParserException

abstract class EqualSignException: ParserException()

class EqualPositionException: EqualSignException() {
	override val message: String = "Wrong equal sign position"
}
class EqualAmountException: EqualSignException() {
	override val message: String = "Wrong amount of equal signs"
}