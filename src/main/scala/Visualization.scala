import java.awt.Dimension
import javax.swing._
import scala.swing._
import scala.util.Random

/** This class is used to visualize the graph. */

class Visualization {

  JFrame.setDefaultLookAndFeelDecorated(true)
  val frame = new JFrame("Numerical visualization library")
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val graph = new Graph()
  graph.setPreferredSize(new Dimension(1000, 800))
  frame.add(graph)

  def showGrid(show: Boolean) = {
    graph.includeGrid = show
  }

  /** Add a line to the graph */
  def addInput(l: Line): Unit = {
    graph.addLine(l)
  }

  def nameXAxis(name: String) = graph.nameXAxis(name)
  def nameYAxis(name: String) = graph.nameYAxis(name)

  // Show the graph
  def show(): Unit = {
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

}
