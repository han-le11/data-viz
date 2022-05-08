import java.awt.Dimension
import javax.swing._
import scala.swing._
import scala.util.Random

/** This class is used to visualize the graph. */

class Visualization {

  JFrame.setDefaultLookAndFeelDecorated(true)
  val frame = new JFrame("Numerical visualization library")
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  val accessories = new Accessories  // Takes care of axes, grids, etc.
  val graph = new LineGraph(accessories)
  graph.setPreferredSize(new Dimension(1200, 800))
  frame.add(graph)

  def nameLineGraph(name: String) = accessories.changeGraphName(name)
  def nameXAxis(name: String) = accessories.nameXAxis(name)
  def nameYAxis(name: String) = accessories.nameYAxis(name)

  /** Add a line to the graph */
  def addInput(l: Line): Unit = {
    graph.addLine(l)
  }

  def showGrid(show: Boolean) = {
    graph.includeGrid = show
  }

  // Show the graph
  def show(): Unit = {
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

}
