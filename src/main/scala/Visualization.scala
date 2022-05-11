import java.awt.Dimension
import javax.swing._

/** Create graph, add components, and visualize the graph.
  *
  * @constructor create a new Visualization
  */

class Visualization {
  JFrame.setDefaultLookAndFeelDecorated(true)
  val frame = new JFrame("Numerical Visualization Library")  // name of the graphical frame
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  val accessories = new Accessories // Takes care of legend, etc.
  val graph = new LineGraph(accessories)
  graph.setPreferredSize(new Dimension(1200, 800))  // the size when the graph is shown.
  frame.add(graph)

  /** Set a name for the graph.
    *
    * @param name name of the graph
    */
  def nameGraph(name: String): Unit = accessories.changeGraphName(name)


  /** Set a name for the x axis.
    *
    * @param name name for the x axis
    */
  def nameXAxis(name: String): Unit = accessories.changeNameX(name)


  /** Set a name for the y axis.
    *
    * @param name name for the y axis
    */
  def nameYAxis(name: String): Unit = accessories.changeNameY(name)


  /** Set unit for the x axis.
    *
    * @param unit unit for the x axis
    */
  def xUnit(unit: String) = accessories.changeUnitX(unit)


  /** Set unit for the y axis.
    *
    * @param unit unit for the y axis
    */
  def yUnit(unit: String) = accessories.changeUnitY(unit)



  /** Method for the line graph to add a single line to the graph.
    *
    * @param line a trend line
    */
  def addLine(line: Line): Unit = {
    graph.addLine(line)
  }


  /** Choose to show the grid or not.
    *
    * @param gridShown true if the grid is shown, false otherwise.
    */
  def showGrid(gridShown: Boolean): Unit = {
    graph.hasGrid = gridShown
  }


  /** Set the size of grid.
    *
    * @param x the number of grid tiles on x axis
    * @param y the number of grid tiles on y axis
    */
  def gridSize(x: Int, y: Int): Unit = graph.chooseGridSize(x, y)


  /** Show the final graph */
  def show(): Unit = {
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

}
