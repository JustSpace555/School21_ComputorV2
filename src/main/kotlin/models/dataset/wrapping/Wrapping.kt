package models.dataset.wrapping

import models.dataset.DataSet

abstract class Wrapping : DataSet {
	open val listOfOperands: List<DataSet> = listOf()
}