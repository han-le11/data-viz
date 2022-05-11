import java.awt._
import java.awt.geom._
import javax.swing._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/** Line graph component that is created when
  *
  * @param accessories takes care of legend, graph name, names and units of x axis and y axis.
  */
class LineGraph(val accessories: Accessories) extends JPanel {
  val padding = 80 // can be used to adjust the location of components on the graph
  val pointColor = new Color(100, 100, 100, 180)
  val lineStroke = new BasicStroke(2f) // set the width of the trend lines and axes
  val pointWidth = 4
  val defaultColorList = Vector(Color.BLUE, Color.RED, Color.ORANGE, Color.PINK)

  // Default number of ticks on x axis and y axis. These also takes care the grid size,
  // because it looks nice if the locations of grid lines match the locations of ticks.
  // This is a design choice.
  var xTickAndGrid = 10
  var yTickAndGrid = 10

  // Make grid style  and dash attributes
  val dot: Array[Float] = Array(2.0f)
  val gridStroke = new BasicStroke(1.0f,                    // line width
                                  BasicStroke.CAP_ROUND,    // end cap style
                                  BasicStroke.JOIN_ROUND,   // join style
                                  1.0f,                     // miter limit
                                  dot,                      // dashing pattern
                                  0.0f)                     // the offset to start the dashing pattern
  val gridColor = new Color(200, 200, 200, 200)
  var hasGrid: Boolean = _

  var inputLines = new mutable.ListBuffer[Line]  // store the input lines

  /** Add a new trend line to the buffer that stores the input lines. */
  def addLine(l: Line) = {
    inputLines += l
  }

  /** Get a list of (x,y) tuples of all lines. */
  def coordinates() =  {
    var points = Vector[(Double, Double)]()
    for (line <- inputLines) {
      points ++= line.data
    }
    points
  }

  /** Adjust the grid size to match the user's choice. */
  def chooseGridSize(numberOfX: Int, numberOfY: Int): Unit = {
    xTickAndGrid = numberOfX
    yTickAndGrid = numberOfY
  }

  /** Override method paintComponent to draw graphic. This method is called automatically. **/
  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g) // call the super class
    val g2d = g.asInstanceOf[Graphics2D] // cast to Graphics2D to have more control over, e.g., geometry.

    // Smoothen the graphic rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    // Scaling ratio for x and y in pixel:
    val xScale = this.getWidth - 3 * padding
    val yScale = this.getHeight - 3 * padding

    // Get min and max bounds of all inputs
    val xMin = getMin("x")
    val xMax = getMax("x")
    val yMin = getMin("y")
    val yMax = getMax("y")

    val xCoords = inputLines.map(line => line.data.map(_._1).toSet).foldLeft(Set[Double]())((a,b) => a union b) // all x coordinate
    val numberOfXCoords = xCoords.size // number of distinct x coordinates

    // Scale from the given coordinates to pixels, then draw the input lines and points.
    def drawLinesAndPoints(): Unit = {
      g2d.setStroke(lineStroke)
      for (i <- inputLines.indices) {
        if (i < defaultColorList.size) {
          inputLines(i).lineColor = defaultColorList(i)
          g2d.setColor(inputLines(i).lineColor)  // set a line color for each line.
        } else g2d.setColor(inputLines(i).lineColor)  // randomize a color

        val coords = inputLines(i).data  // get tuples (x,y)
        val pixelPoints = mutable.ListBuffer[Point2D.Double]() // a list of all points in pixels

        for (i <- coords.indices) {  // go through the vector of coordinates to scale each point
          val x = ((coords(i)._1 - xMin) / (xMax - xMin)) * xScale + padding * 2
          val y = yScale - ((coords(i)._2 - yMin) / (yMax - yMin)) * yScale + padding
          val newPoint = new Point2D.Double(x, y)
          pixelPoints += newPoint
        }

        for (i <- pixelPoints.indices) {  // draw a trend line
          if (i >= 1) {
            val x1 = pixelPoints(i).x
            val y1 = pixelPoints(i).y
            val x2 = pixelPoints(i - 1).x
            val y2 = pixelPoints(i - 1).y
            val line = new Line2D.Double(x1, y1, x2, y2)
            g2d.draw(line)
          }
        }

        // Plot the points
        g2d.setColor(pointColor)
        for (i <- pixelPoints.indices) {
          val x = (pixelPoints(i).x - pointWidth / 2).toInt
          val y = (pixelPoints(i).y - pointWidth / 2).toInt
          g2d.fillOval(x, y, pointWidth, pointWidth)
        }
      }
    }
    drawLinesAndPoints()

    accessories.addAccessories(g2d, this, inputLines)  // add accessories such as names of axes, graph title, and legend.

    // Draw the grid if the user chooses to show the grid.
    if (hasGrid) {
      drawGrid(g2d, this, xTickAndGrid, yTickAndGrid)
    }
    addAxes()  // add axes, ticks and labels of axes

    // Method to draw the axes, ticks, and labels on axes
    def addAxes(): Unit = {
      g2d.setColor(Color.BLACK)
      val metrics = g2d.getFontMetrics  // Gets the font metrics of the current font.

      if (numberOfXCoords < xTickAndGrid) {  // if there are less than 10 data points for each line
        addXLabels(numberOfXCoords)
      } else {
        addXLabels(xTickAndGrid)
      }

      var yLabel = ""
      for (i <- 0 to yTickAndGrid) {
        val x0 = padding * 2
        val x1 = pointWidth + padding * 2
        val y0 = getHeight - ((i * (getHeight - padding * 3)) / yTickAndGrid + padding * 2)
        val y1 = y0
        g2d.drawLine(x0, y0, x1, y1)  // create ticks for y axis

        yLabel = ((yMin + (yMax - yMin) * ((i * 1.0) / yTickAndGrid)) * 100).asInstanceOf[Int] / 100.0 + ""
        val labelWidth = metrics.stringWidth(yLabel)
        g2d.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight / 2) - 3)  // draw the labels on y axis
      }

      // Draw x axis and y axis
      g2d.setStroke(lineStroke)
      g2d.setColor(Color.BLACK)
      g2d.drawLine(padding * 2, this.getHeight - padding * 2, this.getWidth - padding, this.getHeight - padding * 2) // x axis
      g2d.drawLine(padding * 2, this.getHeight - padding * 2, padding * 2, padding) // y axis
    }

    // Helper function to draw ticks and labels on x axis
    def addXLabels(dataLength: Int) = {
      var xLabel: String = ""
      val metrics = g2d.getFontMetrics  // Gets the font metrics of the current font.
      for (i <- 0 to dataLength) {
        val x0 = i * (this.getWidth - padding * 3) / dataLength + padding * 2
        val x1 = x0
        val y0 = this.getHeight - padding * 2
        val y1 = y0 - pointWidth
        g2d.drawLine(x0, y0, x1, y1)  // draw the ticks on x axis

        // draw the labels on x axis
        xLabel = ((xMin + (xMax - xMin) * ((i * 1.0) / dataLength)) * 100.0).asInstanceOf[Int] / 100.0 + ""
        val labelWidth = metrics.stringWidth(xLabel)
        g2d.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight + 3)
      }
    }

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

  /** Method to draw the grid */
  def drawGrid(g2d: Graphics2D, panel: JPanel, x: Int, y: Int): Unit = {
    g2d.setColor(gridColor)
    g2d.setStroke(gridStroke)

    // draw grid line for x axis
    for (i <- 0 to x) {
      if (inputLines.nonEmpty) {
        val x0 = i * (panel.getWidth - padding * 3) / x + padding * 2
        val x1 = x0
        val y0 = panel.getHeight - padding * 2
        val y1 = y0 - pointWidth
        g2d.drawLine(x0, getHeight - padding * 2 - 1 - pointWidth, x1, padding)
      }
    }
    // draw grid line for y axis
    for (i <- 0 to y) {
      val y0 = getHeight - ((i * (this.getHeight - padding * 3)) / y + padding * 2)
      val y1 = y0
      g2d.drawLine(padding * 2 + 1 + pointWidth, y0, getWidth - padding, y1)
    }
  }

  /*/** Method to check if all x or all y coordinates of the input lines are integers. */
  def isInteger(coordinate: String): Boolean = {
    var checkInt = new mutable.ListBuffer[Boolean]()
    if (coordinate == "x") {
      checkInt = inputLines.map(line => line.data.forall(coord => (coord._1 % 1 == 0)))
      checkInt.forall(_ == true)

    } else {
      checkInt = inputLines.map(line => line.data.forall(coord => (coord._2 % 1 == 0)))
      checkInt.forall(_ == true)
    }
  }*/
}