import java.awt._
import java.awt.geom._
import javax.swing._
import scala.collection.mutable

class Graph extends JPanel {
  val padding = 50 // starting point from the upper left corner
  val pointColor = new Color(100, 100, 100, 180)
  val lineStroke = new BasicStroke(2f) // stroking style for the lines
  val pointWidth = 4
  var xAxisName: String = ""
  var yAxisName: String = ""
  val numberOfTicksX = 10  // number of ticks on x axis
  val numberOfTicksY = 11  // number of ticks on y axis

  val gridColor = new Color(200, 200, 200, 200)
  var includeGrid: Boolean = _

  var inputLines = new mutable.ListBuffer[Line]  // store input lines
  // get the number of points of each line to create ticks on x axis
  //var numberOfTicksX: Int = inputLines.flatMap(line => line.data).size

  /** Get a list of (x,y) tuples of all lines. */
  def coordinates() =  {
    var points = Vector[(Double, Double)]()
    for (line <- inputLines) {
      points ++= line.data
    }
    points
  }

  /** Add a new trend line. */
  def addLine(l: Line) = {
    inputLines += l
  }

  /** Return the minimum x or y of the coordinates */
  def getMin(axis: String): Double = {
    var minCoord = Double.PositiveInfinity
    for (point <- coordinates()) {
      if (axis == "x") {
        minCoord = minCoord.min(point._1)
      } else minCoord = minCoord.min(point._2)
    }
    minCoord
  }

  /** Return the maximum x or y of the input coordinates */
  def getMax(axis: String): Double = {
    var maxCoord = Double.NegativeInfinity
    for (point <- coordinates()) {
      if (axis == "x") {
        maxCoord = maxCoord.max(point._1)
      } else maxCoord = maxCoord.max(point._2)
    }
    maxCoord
  }

  def nameXAxis(name: String) = { xAxisName = name }
  def nameYAxis(name: String) = { yAxisName = name }


  /** Override method paintComponent to draw graphic. This method is called automatically. **/
  override def paintComponent(g: Graphics) {

    super.paintComponent(g) // call the super class
    val g2d = g.asInstanceOf[Graphics2D] // cast to Graphics2D

    // Smoothen the graphic rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    // Scaling ratio for x and y:
    val xScale = this.getWidth - 3 * padding
    val yScale = this.getHeight - 3 * padding

    // Get min and max bounds of all inputs
    val xMin = getMin("x")
    val xMax = getMax("x")
    val yMin = getMin("y")
    val yMax = getMax("y")

    // Scale from the given coordinates to pixels, then draw the lines.
    def scaleAndDrawLines(): Unit = {
      g2d.setStroke(lineStroke)
      for (line <- inputLines) {
        g2d.setColor(line.lineColor) // get a different color for each line.
        val coords = line.data // tuples (x,y)
        val pixelPoints = mutable.ListBuffer[Point2D.Double]() // a list of points in pixels

        for (i <- coords.indices) {  // go through the vector of coordinates to scale each point
          val x = ((coords(i)._1 - xMin) / (xMax - xMin)) * xScale + padding * 2
          val y = yScale - ((coords(i)._2 - yMin) / (yMax - yMin)) * yScale + padding
          val newPoint = new Point2D.Double(x, y)
          pixelPoints += newPoint
        }

        for (i <- pixelPoints.indices) {  // draw a single line
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
    scaleAndDrawLines()

    // Method to draw the grid
    def drawGrid() = {
      for (i <- 0 to numberOfTicksX) { // grid line for x axis
        if (inputLines.nonEmpty) {
          val x0 = i * (getWidth - padding * 3) / numberOfTicksX + padding * 2
          val x1 = x0
          val y0 = getHeight - padding * 2
          val y1 = y0 - pointWidth
          g2d.setColor(gridColor)
          g2d.drawLine(x0, getHeight - padding * 2 - 1 - pointWidth, x1, padding)
        }
      }

      for (i <- 0 to numberOfTicksY) {  // grid line for y axis
        val y0 = getHeight - ((i * (getHeight - padding * 3)) / numberOfTicksY + padding * 2)
        val y1 = y0
        g2d.setColor(gridColor)
        g2d.drawLine(padding * 2 + 1 + pointWidth, y0, getWidth - padding, y1)
      }
    }
    if (includeGrid) drawGrid()


    def addAxisNames(): Unit = {
      val font = new Font("Serif", Font.PLAIN, 60)
      /*g2d.drawString(, this.getHeight / 2, this.getHeight / 2)  // name and (x,y) location
      g2d.drawString()*/
    }
    addAxisNames()

    // Method to draw the ticks and labels on axes
    def drawLabelsAndTicks(): Unit = {
      g2d.setColor(Color.BLACK)
      val metrics = g2d.getFontMetrics  // Gets the font metrics of the current font.
      for (i <- 0 to numberOfTicksX) {
        val x0 = i * (getWidth - padding * 3) / numberOfTicksX + padding * 2
        val x1 = x0
        val y0 = getHeight - padding * 2
        val y1 = y0 - pointWidth
        g2d.drawLine(x0, y0, x1, y1)  // draw the ticks on x axis

        var xLabel: String = ""

        if (isInteger("x")) {  // if all x values are integers
          xLabel = ((xMin + (xMax - xMin) * ((i * 1.0) / numberOfTicksX)) * 100).asInstanceOf[Int] / 100 + ""
          val labelWidth = metrics.stringWidth(xLabel)
          g2d.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight + 3)  // name and location of label on x axis

        } else if (!isInteger("x")) {  // if not all x values are integers
          xLabel = ((xMin + (xMax - xMin) * ((i * 1.0) / numberOfTicksX)) * 100.0).asInstanceOf[Int] / 100.0 + ""
          val labelWidth = metrics.stringWidth(xLabel)
          g2d.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight + 3)  // draw the labels on x axis
        }
      }

      for (i <- 0 to numberOfTicksY) {
        val x0 = padding * 2
        val x1 = pointWidth + padding * 2
        val y0 = getHeight - ((i * (getHeight - padding * 3)) / numberOfTicksY + padding * 2)
        val y1 = y0
        g2d.drawLine(x0, y0, x1, y1)  // create ticks for y axis

        if (inputLines.nonEmpty) {
          val yLabel = ((yMin + (yMax - yMin) * ((i * 1.0) / numberOfTicksY)) * 100).asInstanceOf[Int] / 100.0 + ""
          val labelWidth = metrics.stringWidth(yLabel)
          g2d.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight / 2) - 3) // draw the labels on x axis
        }
      }
    }
    drawLabelsAndTicks()

    // Draw x axis and y axis
      g2d.setColor(Color.BLACK)
      g2d.drawLine(padding * 2, this.getHeight() - padding * 2,
        this.getWidth() - padding, this.getHeight() - padding * 2) // x axis
      g2d.drawLine(padding * 2, this.getHeight() - padding * 2, padding * 2, padding) // y axis
  }

  /** Method to check if all x or all y coordinates of the input lines are integers. */
  def isInteger(coordinate: String): Boolean = {
    var checkInt = new mutable.ListBuffer[Boolean]()
    if (coordinate == "x") {
      checkInt = inputLines.map(line => line.data.forall(coord => (coord._1 % 1 == 0)))
      if (checkInt.forall(_ == true)) true else false

    } else {
      checkInt = inputLines.map(line => line.data.forall(coord => (coord._2 % 1 == 0)))
      if (checkInt.forall(_ == true)) true else false
    }
  }

}