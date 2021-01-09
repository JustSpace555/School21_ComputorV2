package graph

//import javafx.application.Application
//import javafx.scene.Scene
//import javafx.scene.chart.LineChart
//import javafx.scene.chart.NumberAxis
//import javafx.scene.chart.XYChart
//import javafx.stage.Stage
//import models.dataset.DataSet
//import models.dataset.Function
//import models.dataset.numeric.SetNumber
//import models.exceptions.computorv2.graphexception.ImpossibleToVisualizeData
//import models.exceptions.computorv2.graphexception.WrongDataFormatForGraph
//import kotlin.properties.Delegates
//
//class Graph : Application() {
//
//	private lateinit var input: DataSet
//	private var startDot by Delegates.notNull<Double>()
//	private var finishDot by Delegates.notNull<Double>()
//	private var shift by Delegates.notNull<Double>()
//
//	private val dotsX = ArrayList<Double>()
//	private val dotsY = ArrayList<Double>()
//
//	override fun start(stage: Stage?) {
//		if (stage == null) return
//
//		when (input) {
//			is SetNumber -> {
//				dotsX.addAll(listOf(startDot, finishDot))
//				dotsY.addAll(listOf((input as SetNumber).number.toDouble(), (input as SetNumber).number.toDouble()))
//			}
//			is Function -> {
//				var i = startDot
//				while (i <= finishDot) {
//					dotsX.add(i)
//					val dot = when(val res = (input as Function).invoke(i)) {
//						is SetNumber -> res.number.toDouble()
//						else -> throw ImpossibleToVisualizeData(res)
//					}
//					dotsY.add(dot)
//					i += shift
//				}
//			}
//			else -> throw WrongDataFormatForGraph(input)
//		}
//
//		stage.title = input.toString()
//
//		val xAxis = NumberAxis()
//		val yAxis = NumberAxis()
//		val lineChart = LineChart(xAxis, yAxis).apply {
//			title = "Graph of $input"
//			createSymbols = false
//		}
//		val seriesFun = XYChart.Series<Number, Number>()
//
//		dotsY.forEachIndexed { index, d ->
//			seriesFun.data.add(XYChart.Data(dotsX[index], d))
//		}
//
//		val scene = Scene(lineChart, 1280.0, 1024.0)
//		lineChart.data.add(seriesFun)
//
//		stage.scene = scene
//		stage.show()
//	}
//
//	fun draw(input: DataSet, startDot: Double, finishDot: Double, shift: Double) {
//		this.input = input
//		this.startDot = startDot
//		this.finishDot = finishDot
//		this.shift = shift
//		launch()
//	}
//}