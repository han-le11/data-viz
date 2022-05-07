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
  graph.setPreferredSize(new Dimension(800, 600))
  frame.add(graph)

  var xAxisName = graph.xAxisName
  var yAxisName = graph.yAxisName

  def showGrid(show: Boolean) = {
    graph.includeGrid = show
  }

  /** Add a line to the graph */
  def addInput(l: Line): Unit = {
    graph.addLine(l)
  }

  // Show the graph
  def show(): Unit = {
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

}
