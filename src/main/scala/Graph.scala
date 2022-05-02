import java.awt._
import java.awt.geom._
import javax.swing._
import scala.collection.mutable

class Graph extends JPanel {
  val padding = 25 // starting point from the upper left corner
  //val labelPadding = 25
  val pointColor = new Color(100, 100, 100, 180)
  val lineStroke = new BasicStroke(2f) // stroking style for the lines
  val pointWidth = 4
  val xDivision = 11  // number of ticks on x axis
  val yDivisions = 11  // number of ticks on y axis

  val gridColor = new Color(200, 200, 200, 200)
  var includeGrid: Boolean = _

  var inputLines = new mutable.ListBuffer[Line]  // store input lines

  /** Get a list of (x,y) tuples of all lines. **/
  def coordinates() =  {
    var points = Vector[(Double, Double)]()
    for (line <- inputLines) {
      points ++= line.data
    }
    points
  }

  /** Add a new trend line. * */
  def addLine(l: Line) = {
    inputLines += l
  }

  /** Return the minimum x or y of the coordinates **/
  def getMin(axis: String): Double = {
    var minCoord = Double.PositiveInfinity
    for (point <- coordinates()) {
      if (axis == "x") {
        minCoord = minCoord.min(point._1)
      } else minCoord = minCoord.min(point._2)
    }
    minCoord
  }

  /** Return the maximum x or y of the input coordinates **/
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
    val xScale = this.getWidth - 3 * padding
    val yScale = this.getHeight - 3 * padding

    // Find min and max bounds of all inputs
    val xMin = getMin("x")
    val xMax = getMax("x")
    val yMin = getMin("y")
    val yMax = getMax("y")

    // Scaling from the given coordinates to pixels, then draw the lines.
    def scaleAndDraw(): Unit = {
      g2d.setStroke(lineStroke)
      for (line <- inputLines) {
        g2d.setColor(line.lineColor) // get a different color for each line.
        val coords = line.data // tuples (x,y)
        val pixelPoints = mutable.ListBuffer[Point2D.Double]() // a list of points in pixels

        for (i <- coords.indices) { // go through the vector of coordinates to scale each point
          val x = ((coords(i)._1 - xMin) / (xMax - xMin)) * xScale + padding * 2
          val y = yScale - ((coords(i)._2 - yMin) / (yMax - yMin)) * yScale + padding
          val newPoint = new Point2D.Double(x, y)
          pixelPoints += newPoint
        }

        for (i <- pixelPoints.indices) { // draw a single line
          if (i >= 1) {
            val x1 = pixelPoints(i).x
            val y1 = pixelPoints(i).y
            val x2 = pixelPoints(i - 1).x
            val y2 = pixelPoints(i - 1).y
            val line = new Line2D.Double(x1, y1, x2, y2)
            g2d.draw(line)
          }
        }
      }
    }
    scaleAndDraw()

    // draw the grid
    def drawGrid() = {
      for (i <- 0 to xDivision) { // grid line for x axis
        if (inputLines.nonEmpty) {
          val x0 = i * (getWidth - padding * 3) / (inputLines.size - 1) + padding * 2
          val x1 = x0
          val y0 = getHeight - padding * 2
          val y1 = y0 - pointWidth

          if ((i % ((inputLines.size / 20.0).asInstanceOf[Int] + 1)) == 0) {
            g2d.setColor(gridColor)
            g2d.drawLine(x0, getHeight - padding * 2 - 1 - pointWidth, x1, padding)
            g2d.setColor(Color.BLACK)
            val xLabel = i + ""
            val metrics = g2d.getFontMetrics
            val labelWidth = metrics.stringWidth(xLabel)
            g2d.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight + 3)
          }
        }
      }
      for (i <- 0 to yDivisions) {  // grid line for y axis
        val y0 = getHeight - ((i * (getHeight - padding * 3)) / yDivisions + padding * 2)
        val y1 = y0
        g2d.setColor(gridColor)
        g2d.drawLine(padding * 2 + 1 + pointWidth, y0, getWidth - padding, y1)
      }
    }
    if (includeGrid) drawGrid()

    def drawTicks(): Unit = {
      for (i <- 0 to xDivision) { // create ticks for x axis
        if (inputLines.nonEmpty) {
          val x0 = i * (getWidth - padding * 3) / (inputLines.size - 1) + padding * 2
          val x1 = x0
          val y0 = getHeight - padding * 2
          val y1 = y0 - pointWidth
          g2d.drawLine(x0, y0, x1, y1)
        }
      }

      // create ticks for y axis
      for (i <- 0 to yDivisions) {
        val x0 = padding * 2
        val x1 = pointWidth + padding * 2
        val y0 = getHeight - ((i * (getHeight - padding * 3)) / yDivisions + padding * 2)
        val y1 = y0
        if (inputLines.nonEmpty) {
          g2d.setColor(Color.BLACK)
          val yTick = ((yMin + (yMax - yMin) * ((i * 1.0) / yDivisions)) * 100).asInstanceOf[Int] / 100.0 + ""
          val metrics = g2d.getFontMetrics
          val labelWidth = metrics.stringWidth(yTick)
          g2d.drawString(yTick, x0 - labelWidth - 5, y0 + (metrics.getHeight / 2) - 3)
        }
        g2d.drawLine(x0, y0, x1, y1)
      }
    }
    drawTicks()

    // Draw x and y axes
      g2d.setColor(Color.BLACK)
      g2d.drawLine(padding * 2, this.getHeight() - padding * 2,
        this.getWidth() - padding, this.getHeight() - padding * 2) // x axis
      g2d.drawLine(padding * 2, this.getHeight() - padding * 2, padding * 2, padding) // y axis

  }

}