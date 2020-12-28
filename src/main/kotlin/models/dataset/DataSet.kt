package models.dataset

import models.dataset.numeric.SetNumber

interface DataSet {
	operator fun plus(other: DataSet): DataSet
	operator fun minus(other: DataSet): DataSet
	operator fun times(other: DataSet): DataSet
	operator fun div(other: DataSet): DataSet
	operator fun rem(other: DataSet): DataSet

	fun pow(other: DataSet): DataSet
	fun pow(other: Number): DataSet = pow(SetNumber(other))

	override fun toString(): String
}