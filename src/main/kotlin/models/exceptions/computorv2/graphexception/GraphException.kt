package models.exceptions.computorv2.graphexception

import models.dataset.DataSet
import models.exceptions.ComputorException
import models.exceptions.computorv2.ComputorV2Exception

@ComputorV2Exception
abstract class GraphException : ComputorException()

@ComputorV2Exception
class WrongDataFormatForGraph(input: DataSet) : GraphException() {
	override val message: String = "It is impossible to visualize ${input::class.simpleName}:\n$input"
}

@ComputorV2Exception
class ImpossibleToVisualizeData(input: DataSet) : GraphException() {
	override val message: String = "It is impossible to visualize function output ${input::class.simpleName}:\n$input"
}