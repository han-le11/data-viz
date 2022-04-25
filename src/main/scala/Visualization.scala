import java.awt.Dimension
import javax.swing._
import scala.swing._
import java.util
import scala.util.Random

class Visualization {

  // Read the input file
  //def readFile

  JFrame.setDefaultLookAndFeelDecorated(true)
  val frame = new JFrame("Numerical visualization library")
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val graph = new Graph()
  graph.setPreferredSize(new Dimension(800, 600))
  frame.add(graph) // frame can only take one object?

  def addInput(l: Line): Unit = {
    graph.addLine(l)
  }

  // for only 1 line
  /*def line(coords: Vector[(Double, Double)]): Unit = {
    graph.line(coords)
  }*/

  // Show the graph
  def show(): Unit = {
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

}
