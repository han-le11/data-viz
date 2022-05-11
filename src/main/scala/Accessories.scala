import java.awt._
import java.awt.geom._
import javax.swing._
//import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/** This class takes care of legend, graph name, names and units of x axis and y axis. */
class Accessories {
  val padding = 30
  var graphName = "Graph"  // default name of the graph

  val lineStroke = new BasicStroke(2f) // set the width of the trend lines and axes
  var xAxisName: String = "X"
  var yAxisName: String = "Y"
  var xUnit: String = ""
  var yUnit: String = ""

  /** Change name of the graph */
  def changeGraphName(name: String) = { graphName = name }

  /** Change name of the x axis and y axis. */
  def changeNameX(name: String) = { xAxisName = name }
  def changeNameY(name: String) = { yAxisName = name }

  /** Change unit of the x axis and y axis. */
  def changeUnitX(unit: String) = { xUnit = unit }
  def changeUnitY(unit: String) = { yUnit = unit }

  def addAccessories(g2d: Graphics2D, panel: JPanel, inputs: ListBuffer[Line]): Unit = {
    addAxisTitles(g2d, panel)
    addLegend(g2d, panel, inputs)
    showGraphName(g2d, panel)
  }

  def showGraphName(g2d: Graphics2D, panel: JPanel): Unit = {
    val font = new Font("Serif", Font.PLAIN, 400)
    g2d.drawString(graphName, panel.getWidth / 2, padding)
  }


  /** Add axis titles and units. Only shows units if they are defined by the user. */
  def addAxisTitles(g2d: Graphics2D, panel: JPanel): Unit = {
    g2d.setColor(Color.BLACK)
    val font = new Font("Serif", Font.PLAIN, 60)

    if (xUnit == "" && !(yUnit == "")) {  // if the user does not choose the units for x axis
      g2d.drawString(xAxisName, panel.getWidth/2, panel.getHeight - padding * 4) // name of x axis and its location
      g2d.drawString(yAxisName + " (" + yUnit + ")", padding, panel.getHeight / 2) // name of y axis and its location

    } else if (!(xUnit == "") && yUnit == "") {
      g2d.drawString(xAxisName + " (" + xUnit + ")", panel.getWidth/2, panel.getHeight - padding * 4)
      g2d.drawString(yAxisName, padding, panel.getHeight / 2)

    } else if (xUnit == "" && yUnit == "") {
      g2d.drawString(xAxisName, panel.getWidth/2, panel.getHeight - padding * 4)
      g2d.drawString(yAxisName, padding, panel.getHeight / 2)

    } else {
      g2d.drawString(xAxisName + " (" + xUnit + ")", panel.getWidth/2, panel.getHeight - padding * 4)
      g2d.drawString(yAxisName + " (" + yUnit + ")", padding, panel.getHeight / 2)
    }
  }


  def addLegend(g2d: Graphics2D, panel: JPanel, inputs: ListBuffer[Line]): Unit = {
    for (i <- inputs.indices) {
      // Draw a square corresponding to each color
      val colorLabel = new Rectangle2D.Double(padding * i * 4 + padding * 2, panel.getHeight - padding,
      panel.getWidth * 0.01, panel.getWidth * 0.01)
      g2d.setColor(inputs(i).lineColor)  // take the current line color
      g2d.draw(colorLabel)
      g2d.fill(colorLabel)

      // Set text color of the line names and then add them to the graph
      g2d.setColor(Color.BLACK)
      g2d.drawString(inputs(i).name, padding * i * 4 + padding * 2, panel.getHeight - padding * 2)
    }
  }

}
