package models.exceptions.computorv2.graphexception

//import models.dataset.DataSet
//import models.exception.ComputorException
//import models.exception.computorv2.ComputorV2Exception
//
//@ComputorV2Exception
//abstract class GraphException : ComputorException()
//
//@ComputorV2Exception
//class WrongDataFormatForGraph(input: DataSet) : GraphException() {
//	override val message: String = "It is impossible to visualize ${input::class.simpleName}:\n$input"
//}
//
//@ComputorV2Exception
//class ImpossibleToVisualizeData(input: DataSet) : GraphException() {
//	override val message: String = "It is impossible to visualize function output ${input::class.simpleName}:\n$input"
//}
//
//@ComputorV2Exception
//class CanNotParsePlotInputFunction : GraphException() {
//	override val message: String = "Wrong input of plot function. It must be: " +
//			"plot('parameter')['start dot', 'finish dot', shift value'] = ..."
//}