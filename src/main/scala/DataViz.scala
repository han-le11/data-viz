/** Demo of a specific use case. This dataset is downloaded from Kaggle. */

object DataViz {
  def main(args: Array[String]): Unit = {
    val demoGraph = new Visualization()
    demoGraph.showGrid(true) // set the graph to show

    val inputFile = "example/silver.csv"
    val reader = new CSVReader(inputFile)
    val data = reader.records

    // choose the range of values for x axis
    val xAxis = Vector.range(1, data("Date").size + 1, 1).map(_.toDouble)

    // choose columns Open,High,Low,Close and the last 31 values of each column to visualize.
    val open = new Line("Open", (xAxis zip (data("Open").map(x => x.toDouble))).takeRight(10))
    demoGraph.addInput(open)

    val close = new Line("Close", (xAxis zip data("Close").map(x => x.toDouble)).takeRight(10))
    demoGraph.addInput(close)

    val high = new Line("High", (xAxis zip data("High").map(x => x.toDouble)).takeRight(10))
    demoGraph.addInput(high)

    val low = new Line("Low", (xAxis zip data("Low").map(x => x.toDouble)).takeRight(10))
    demoGraph.addInput(low)

    demoGraph.show()
  }
}