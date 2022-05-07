/** Demo of a specific use case. This dataset is downloaded from Kaggle. */

object Demo {
  def main(args: Array[String]): Unit = {
    val demoGraph = new Visualization()

    val inputFile = "example/silver.csv"
    val reader = new CSVReader(inputFile)
    val data = reader.records

    demoGraph.xAxisName = "date"
    demoGraph.yAxisName = "price"
    demoGraph.showGrid(true) // choose to show the graph

    // choose the range of values for x axis
    val xAxis = Vector.range(1, data("Date").size + 1, 1).map(_.toDouble)

    // choose columns Open,High,Low,Close and the last n values of each column to visualize.
    val open = new Line("Open", (xAxis zip (data("Open").map(x => x.toDouble))).takeRight(20))
    demoGraph.addInput(open)
    println(open.data)

    val close = new Line("Close", (xAxis zip data("Close").map(x => x.toDouble)).takeRight(20))
    demoGraph.addInput(close)

    val high = new Line("High", (xAxis zip data("High").map(x => x.toDouble)).takeRight(20))
    demoGraph.addInput(high)

    val low = new Line("Low", (xAxis zip data("Low").map(x => x.toDouble)).takeRight(20))
    demoGraph.addInput(low)

    demoGraph.show()
  }
}