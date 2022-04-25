import scala.collection.mutable

object DataViz {

    def main(args: Array[String]): Unit = {
        val graph = new Visualization()

        val input = Vector(
            new Line(label="Jade", data=Vector((3, 2.3), (4, 2.6), (5, 4.2))),
            new Line(label="Cindy", data=Vector((2, 5.5), (4, 2), (5, 3))),
            new Line("Han", data=Vector((2,3), (3, 3), (4, 3), (5,3)))
        )
       input.foreach(i => graph.addInput(i))

        //graph.line(Vector((30, 90), (40,70), (90, 120), (120, 40)))
        graph.show()
    }
}