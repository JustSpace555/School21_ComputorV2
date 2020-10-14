package models.math.dataset

data class SetNumber(val input: Number): DataSet {
	override fun toString(): String = input.toString()
}