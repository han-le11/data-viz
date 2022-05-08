import java.awt._
import scala.util.Random

/** A single line has name (which can be chosen by the user) and data (i.e., tuples of x and y coordinates).
  * lineColor generates a random color.
  */

class Line(val name: String, val data: Vector[(Double, Double)]) {

  val rand = new Random()

  val r = (rand.nextFloat).toFloat
  val g = (rand.nextFloat).toFloat
  val b = (rand.nextFloat).toFloat

  val lineColor = new Color(r, g, b)

}
