package models.math.dataset

interface DataSet {
	operator fun plus(other: DataSet): DataSet
	operator fun minus(other: DataSet): DataSet
	operator fun times(other: DataSet): DataSet
	operator fun div(other: DataSet): DataSet
	operator fun rem(other: DataSet): DataSet

	override fun toString(): String
}