package graph

//import models.dataset.DataSet
//import models.exceptions.computorv2.graphexception.CanNotParsePlotInputFunction
//import parser.getparseable.checkIfFunction
//
//fun parsePlotInput(beforeEqual: List<String>, afterEqual: List<String>): Triple<DataSet, Pair<Double, Double>, Double> {
//	if (!checkIfFunction(beforeEqual)) throw CanNotParsePlotInputFunction()
//
//	val indexOfOpenSquareBracket = beforeEqual.indexOf("[")
//	val indexOfCloseSquareBracket = beforeEqual.indexOf("]")
//
//	if (indexOfOpenSquareBracket == -1 || indexOfCloseSquareBracket == -1) throw CanNotParsePlotInputFunction()
//
//	val listOfData = beforeEqual.subList(indexOfOpenSquareBracket + 1, indexOfCloseSquareBracket)
//	if (listOfData.count { it == "," } != 2) throw CanNotParsePlotInputFunction()
//	val listOfDataLists = listOfData
//}