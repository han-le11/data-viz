import java.awt._
import java.awt.geom._
import java.util.Collections.rotate
import javax.swing._
import scala.collection.mutable.ListBuffer

/** Takes care of axes, grids, etc. */
class Accessories {
  val gridColor = new Color(200, 200, 200, 200)
  val numberOfTicksX = 10  // number of ticks on x axis
  val numberOfTicksY = 10  // number of ticks on y axis
  val padding = 30
  var graphName = "Graph"
  var xAxisName: String = "X"
  var yAxisName: String = "Y"

  /** Change name of the graph */
  def changeGraphName(name: String) = { graphName = name }

  /** Change name of the axes. */
  def nameXAxis(name: String) = { xAxisName = name }
  def nameYAxis(name: String) = { yAxisName = name }

  def draw(g2d: Graphics2D, panel: JPanel, inputs: ListBuffer[Line]): Unit = {
    drawAxisTitles(g2d, panel)
    drawLegend(g2d, panel, inputs)
    showGraphName(g2d, panel)
    // drawGrid(g2d)
  }

  private def showGraphName(g2d: Graphics2D, panel: JPanel): Unit = {
    val font = new Font("Serif", Font.PLAIN, 120)
    g2d.drawString(graphName, panel.getWidth / 2, padding)
  }

  private def drawAxisTitles(g2d: Graphics2D, panel: JPanel): Unit = {
      g2d.setColor(Color.BLACK)
      val font = new Font("Serif", Font.PLAIN, 60)
      /*val affineTransform = new AffineTransform
      affineTransform.rotate(Math.toRadians(45), 0, 0)
      val rotated = font.deriveFont(affineTransform)*/
      g2d.drawString(xAxisName, panel.getWidth/2, panel.getHeight - padding * 4) // name of x axis and location
      g2d.drawString(yAxisName, padding, panel.getHeight / 2)  // name of y axis and location
  }

  private def drawLegend(g2d: Graphics2D, panel: JPanel, inputs: ListBuffer[Line]): Unit = {
    for (i <- inputs.indices) {
      // draw a square corresponding to each color
      val colorLabel = new Rectangle2D.Double(padding * i * 4 + padding * 2, panel.getHeight - padding,
      panel.getWidth * 0.01, panel.getWidth * 0.01)
      g2d.setColor(inputs(i).lineColor)  // take the current line color
      g2d.draw(colorLabel)
      g2d.fill(colorLabel)

      // Set color and draw the names
      g2d.setColor(Color.BLACK)
      g2d.drawString(inputs(i).name, padding * i * 4 + padding * 2, panel.getHeight - padding * 2)
    }
  }

}
