package graph

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.stage.Stage
import models.dataset.DataSet
import models.dataset.Function
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.graphexception.ImpossibleToVisualizeData
import models.exceptions.computorv2.graphexception.WrongDataFormatForGraph

private const val SHIFT = 0.1
private const val START_DOT_X = -10.0
private const val STOP_DOT_X = 10.0

class Graph(private val input: DataSet) : Application() {

	private val dotsX = ArrayList<Double>()
	private val dotsY = ArrayList<Double>()

	override fun init() {
		super.init()

		when (input) {
			is SetNumber -> {
				dotsX.addAll(listOf(START_DOT_X, STOP_DOT_X))
				dotsY.addAll(listOf(input.number.toDouble(), input.number.toDouble()))
			}
			is Function -> {
				var i = START_DOT_X
				while (i <= STOP_DOT_X) {
					dotsX.add(i)
					val dot = when(val res = input.invoke(i)) {
						is SetNumber -> res.number.toDouble()
						else -> throw ImpossibleToVisualizeData(res)
					}
					dotsY.add(dot)
					i += SHIFT
				}
			}
			else -> throw WrongDataFormatForGraph(input)
		}
	}

	override fun start(stage: Stage?) {
		if (stage == null) return

		stage.title = input.toString()

		val xAxis = NumberAxis()
		val yAxis = NumberAxis()
		val lineChart = LineChart(xAxis, yAxis).apply {
			title = "Graph of $input"
			createSymbols = false
		}
		val seriesFun = XYChart.Series<Number, Number>()

		dotsY.forEachIndexed { index, d ->
			seriesFun.data.add(XYChart.Data(dotsX[index], d))
		}

		val scene = Scene(lineChart, 1280.0, 1024.0)
		lineChart.data.add(seriesFun)

		stage.scene = scene
		stage.show()
	}

	fun draw() = launch()
}