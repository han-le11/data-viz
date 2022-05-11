import java.awt.Dimension
import javax.swing._
//import scala.util.Random

/** This is the class that user can use to visualize the graph.
  *
  * */

class Visualization {
  JFrame.setDefaultLookAndFeelDecorated(true)
  val frame = new JFrame("Numerical Visualization Library")  // name of the graphical frame
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  val accessories = new Accessories // Takes care of axes, grids, etc.
  val graph = new LineGraph(accessories)
  graph.setPreferredSize(new Dimension(1200, 800))
  frame.add(graph)

  def nameLineGraph(name: String) = accessories.changeGraphName(name)

  def nameXAxis(name: String) = accessories.changeNameX(name)

  def nameYAxis(name: String) = accessories.changeNameY(name)

  def xUnit(unit: String) = accessories.changeUnitX(unit)

  def yUnit(unit: String) = accessories.changeUnitY(unit)

  /** Method for line graph to add a single line to the graph.
    *
    * @param line
    */
  def addInput(line: Line): Unit = {
    graph.addLine(line)
  }

  /** Choose to show the grid or not.
    *
    * @param includeGrid
    */
  def showGrid(includeGrid: Boolean) = {
    graph.hasGrid = includeGrid
  }

  /** Show the graph */
  def show(): Unit = {
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

}
