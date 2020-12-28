package models.exceptions.computorv1.parserexception

import models.exceptions.computorv1.ComputorV1Exception
import models.exceptions.globalexceptions.parser.SignException

@ComputorV1Exception
abstract class EqualSignException: SignException()

@ComputorV1Exception
class EqualSignPositionException: EqualSignException() {
	override val message: String = "Wrong equal sign position"
}

@ComputorV1Exception
class EqualSignAmountException: EqualSignException() {
	override val message: String = "Wrong amount of equal signs"
}