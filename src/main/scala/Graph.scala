import java.awt.{Dimension, Graphics}
import javax.swing.{JComponent, JPanel}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.swing.Graphics2D

class Graph(val w: Int, val h: Int) extends JComponent {

  var lines: mutable.Seq[(Int, Int)] = ListBuffer[(Int, Int)]()

  def line(coords: List[(Int, Int)]): Unit = {
    lines ++= coords
  }

  // line drawing method
  override def paintComponent(g: Graphics) {
    for (i <- lines.indices) {
      if (i >= 1) {
        g.drawLine(lines(i-1)._1, lines(i-1)._2, lines(i)._1, lines(i)._2)
      }
    }
  }



}