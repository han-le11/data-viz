import scala.collection.mutable

object DataViz {

  def main(args: Array[String]): Unit = {
    val graph = new Visualization()

    val inputFile = "example/silver.csv"
    val reader = new CSVReader(inputFile)
    val data = reader.records
    val xAxis = Vector.range(0, 31, 1).map(_.toDouble)

    // choose necessary columns. Columns Open,High,Low,Close are labels.
    val open = new Line("Open", (xAxis zip data("Open").map(x => x.toDouble)).takeRight(31))
    graph.addInput(open)

    val close = new Line("Close", (xAxis zip data("Close").map(x => x.toDouble)).takeRight(31))
    graph.addInput(close)

    val high = new Line("High", (xAxis zip data("High").map(x => x.toDouble)).takeRight(31))
    graph.addInput(high)

    val low = new Line("Low", (xAxis zip data("Low").map(x => x.toDouble)).takeRight(31))
    graph.addInput(low)

    graph.show()

  }
}