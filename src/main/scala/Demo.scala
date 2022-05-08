/** Demo of a specific use case. This dataset is downloaded from Kaggle. */

object Demo {
  def main(args: Array[String]): Unit = {
    val demoGraph = new Visualization()

    val inputFile = "example/silver.csv"
    val reader = new CSVReader(inputFile)
    val data = reader.records

    demoGraph.nameXAxis("day number")
    demoGraph.nameYAxis("price")
    demoGraph.showGrid(true)  // choose to show the graph

    // User can choose the range of values for x axis
    val xAxis = Vector.range(1, data("Date").size + 1, 1).map(_.toDouble)

    // choose columns Open,High,Low,Close and the last n values of each column to visualize.
    val open = new Line("Open price", (xAxis zip (data("Open").map(x => x.toDouble))).takeRight(30))
    demoGraph.addInput(open)

    val close = new Line("Close price", (xAxis zip data("Close").map(x => x.toDouble)).takeRight(30))
    demoGraph.addInput(close)

    val high = new Line("Highest price", (xAxis zip data("High").map(x => x.toDouble)).takeRight(30))
    demoGraph.addInput(high)

    val low = new Line("Lowest price", (xAxis zip data("Low").map(x => x.toDouble)).takeRight(30))
    demoGraph.addInput(low)

    demoGraph.show()
  }
}