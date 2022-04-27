import java.awt._
import java.awt.geom._
import scala.util.Random

/** A single line with label (name of the category), data (tuples of x and y coordinates).
  * lineColor generates a random color. This class can be used to make the legend.
  */

class Line(val label: String, val data: Vector[(Double, Double)]) {
  val rand = new Random()

  /*// adjust hue, saturation, and luminance to get pastel colors.
  val hue: Float = random.nextFloat
  val saturation: Float = (rand.nextInt(2000) + 1000) / 10000f // Saturation between 0.1 and 0.3
  val luminance = 0.9f
  val lineColor: Color = Color.getHSBColor(hue, saturation, luminance)*/

  val r: Float = rand.nextFloat
  val g: Float = rand.nextFloat
  val b: Float = rand.nextFloat
  val lineColor = new Color(r, g, b)
}
