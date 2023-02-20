object Demo {
  def main(args: Array[String]): Unit = {
    val demoGraph = new Visualization()
    val reader = new CSVReader("./example/silver.csv")
    val data = reader.records

    // User can choose the range of values for x axis and convert them to Double
    val xAxis = Vector.range(1, data("Date").size + 1, 1).map(_.toDouble)

    // User can choose which columns to visualize.
    // For example, here we can choose columns Open, High, Low, Close and the last 30 values of each column to visualize.
    // Then, add the line to the graph.
    val open = new Line("Open price", (xAxis zip (data("Open").map(y => y.toDouble))).takeRight(30))
    demoGraph.addLine(open)

    val close = new Line("Close price", (xAxis zip data("Close").map(y => y.toDouble)).takeRight(30))
    demoGraph.addLine(close)

    val high = new Line("Highest price", (xAxis zip data("High").map(y => y.toDouble)).takeRight(30))
    demoGraph.addLine(high)

    val low = new Line("Lowest price", (xAxis zip data("Low").map(y => y.toDouble)).takeRight(30))
    demoGraph.addLine(low)

    demoGraph.nameGraph("Silver price")

    demoGraph.nameXAxis("day number")
    demoGraph.nameYAxis("price")

    demoGraph.yUnit("USD") // name the unit of y axis
    demoGraph.showGrid(true) // choose to show the grid

    demoGraph.gridSize(10, 10)

    demoGraph.show()

  }
}
