import java.awt._
import scala.util.Random

/**
  * A trend line component. has name (which can be chosen by the user) and data (i.e., tuples of x and y coordinates).
  * The line color is assigned by variable lineColor which stores the default color list, if there are no more than 4 lines.
  * If there are more than 4 lines, variable lineColor generates a random color.
  * @param name line name chosen by user
  * @param data
  */

class Line(val name: String, val data: Vector[(Double, Double)]) {
  val rand = new Random()
  val r = (rand.nextFloat).toFloat
  val g = (rand.nextFloat).toFloat
  val b = (rand.nextFloat).toFloat

  var lineColor = new Color(r, g, b)

}
