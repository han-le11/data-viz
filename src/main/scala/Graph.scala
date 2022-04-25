import java.awt._
import java.awt.geom._
import javax.swing._
import scala.collection.mutable
import scala.util

class Graph extends JPanel {
  val padding = 25 // starting point from the upper left corner
  val labelPadding = 25
  val pointColor = new Color(100, 100, 100, 180)
  val gridColor = new Color(200, 200, 200, 200)
  val graphStroke = new BasicStroke(2f) // stroking style for the lines
  val pointWidth = 4
  val numberYDivisions = 10

  var inputs = new mutable.ListBuffer[Line]  // list of input lines

  /** Get a list of (x,y) tuples of all lines. **/
  def coordinates() =  {
    var points = Vector[(Double, Double)]()
    for (line <- inputs) {
      points ++= line.data
    }
    points
  }

  /** Add a new line. **/
  def addLine(l: Line) = {
    inputs += l
  }

  /** Return the minimum x or y of the list **/
  def getMin(axis: String): Double = {
    var minCoord = Double.PositiveInfinity
    for (point <- coordinates()) {
      if (axis == "x") {
        minCoord = minCoord.min(point._1)
      } else minCoord = minCoord.min(point._2)
    }
    minCoord
  }

  /** Return the maximum x or y of the list  **/
  def getMax(axis: String): Double = {
    var maxCoord = Double.NegativeInfinity
    for (point <- coordinates()) {
      if (axis == "x") {
        maxCoord = maxCoord.max(point._1)
      } else maxCoord = maxCoord.max(point._2)
    }
    maxCoord
  }


  /** Override method paintComponent to draw graphic. This method is called automatically. **/
  override def paintComponent(g: Graphics) {

    super.paintComponent(g) // call the super class
    val g2d = g.asInstanceOf[Graphics2D] // cast to Graphics2D

    // Smoothen the graphic rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    // Scaling ratio for x and y:
    val xScale = this.getWidth - 2* padding - labelPadding
    val yScale = this.getHeight - 2* padding - labelPadding

    // Find bounds of inputs
    val xMin = getMin("x")
    val xMax = getMax("x")
    val yMin = getMin("y")
    val yMax = getMax("y")

    // Scaling from the given coordinates to pixels, then draw the lines.
    g2d.setStroke(graphStroke)
    def drawAndScale() = {
      for (line <- inputs) {
        g2d.setColor(line.lineColor) // get a different color for each line.
        val coords = line.data // tuples (x,y)
        val pixelPoints = mutable.ListBuffer[Point2D.Double]() // a list of points in pixels

        for (i <- coords.indices) { // go through the vector of coordinates to scale each point
          val x = ((coords(i)._1 - xMin) / (xMax - xMin)) * xScale + padding + labelPadding
          val y = yScale - ((coords(i)._2 - yMin) / (yMax - yMin)) * yScale + padding
          val newPoint = new Point2D.Double(x, y)
          pixelPoints += newPoint
        }

        for (i <- pixelPoints.indices) { // draw the current line
          if (i >= 1) {
            val x1 = pixelPoints(i).x
            val y1 = pixelPoints(i).y
            val x2 = pixelPoints(i - 1).x
            val y2 = pixelPoints(i - 1).y
            val line = new Line2D.Double(x1, y1, x2, y2)
            println(x1, y1, x2, y2)
            g2d.draw(line)
          }
        }
      }
    }

    drawAndScale()

    /*for (i <- points.indices) {
      val x = ((points(i).x - xMin) / (xMax - xMin)) * xScale + padding + labelPadding
      val y = yScale - ((points(i).y - yMin) / (yMax - yMin)) * yScale + padding
      val newPoint = new Point2D.Double(x, y)
      pixelPoints += newPoint
    }*/

    // create hatch marks and grid lines for y axis
    /*for (i <- 0 to numberYDivisions) {
      val x0 = padding + labelPadding
      val x1 = pointWidth + padding + labelPadding
      val y0 = getHeight - ((i * (getHeight - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding)
      val y1 = y0

      if (points.size > 0) {
        g2d.setColor(gridColor)
        g2d.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth - padding, y1)
        g2d.setColor(Color.BLACK)
        val yLabel = ((yMin + (yMax - yMin) * ((i * 1.0) / numberYDivisions)) * 100).asInstanceOf[Int] / 100.0 + ""
        val metrics = g2d.getFontMetrics
        val labelWidth = metrics.stringWidth(yLabel)
        g2d.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight / 2) - 3)
      }
      g2d.drawLine(x0, y0, x1, y1)
    }*/

    // Draw x and y axes
    g2d.setColor(Color.BLACK)
    g2d.drawLine(padding + labelPadding, this.getHeight() - padding - labelPadding,
      padding + labelPadding, padding)
    g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding,
      getWidth() - padding, getHeight() - padding - labelPadding)
  }

}