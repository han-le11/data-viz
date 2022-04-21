object DataViz {

    def main(args: Array[String]): Unit = {
        val graph = new Visualization()
        graph.line(List((100, 200), (200, 100), (100, 399)))
        graph.show()
    }
}

