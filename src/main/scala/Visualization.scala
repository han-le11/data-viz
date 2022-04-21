import scala.swing._
import javax.swing._

class Visualization {

  JFrame.setDefaultLookAndFeelDecorated(true)
  val frame = new JFrame("Jade")
  val graph = new Graph(300, 300)
  frame.add(graph) // frame can only take one object?

  def line(coords: List[(Int, Int)]) = {
    graph.line(coords)
  }

  def show(): Unit = {
    frame.pack()
    frame.setVisible(true)
  }



}
