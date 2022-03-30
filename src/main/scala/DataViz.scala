import scala.swing._
import java.awt.{Color, Composite, FontMetrics, Graphics, Graphics2D}
import java.awt.geom.{Point2D, Line2D}
import scala.io.Source


object DataViz extends SimpleSwingApplication {

    def top = new MainFrame {
        title = "Numerical data visualization"
        val width = 600
        val height = 800
        minimumSize = new java.awt.Dimension(width, height)
        preferredSize = new Dimension(width, height)
        maximumSize = new Dimension(width, height)
    }

    def paint(g: Graphics2D, x1: Double, x2: Double, y1: Double, y2: Double): Unit = {
        g.draw(new Line2D.Double(x1, y1, x2, y2))
        g.setColor(Color.blue)
    }

    /*trait Command {
        def execute()
    }*/

}
