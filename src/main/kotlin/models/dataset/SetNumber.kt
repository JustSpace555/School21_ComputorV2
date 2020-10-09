package models.dataset

data class SetNumber(val input: Number): DataSet {
	override fun toString(): String = input.toString()
}