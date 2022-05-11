import java.awt._
import scala.util.Random

/**
  * A trend line.
  *
  * @constructor Create a trend line with `name` and `data`
  * @param name Line name
  * @param data Vector of tuples of (x,y) coordinates
  */

class Line(val name: String, val data: Vector[(Double, Double)]) {
  val rand = new Random()
  val r = rand.nextFloat
  val g = rand.nextFloat
  val b = rand.nextFloat
  var lineColor = new Color(r, g, b)
}
