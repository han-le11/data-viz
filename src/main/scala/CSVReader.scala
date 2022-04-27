import java.io._
import scala.collection.mutable

/** Read an input CSV file */

class CSVReader(val inputFile: String) {

  // Key as the string, vector of strings as values
  var records = new mutable.LinkedHashMap[String, Vector[String]]

  try {
    val fileIn = new FileReader(inputFile)
    val linesIn = new BufferedReader(fileIn)

    // open the streams
    try {
      // the first line contains column names that can be used as labels
      val labels = linesIn.readLine().split(",")
      for (i <- 0 until labels.size) {
        records += (labels(i) -> Vector[String]())
      }

      var oneLine: String = null
      while ({oneLine = linesIn.readLine(); oneLine != null}) {
        val values = oneLine.split(",")  // use comma as separator
        for (i <- 0 until values.size) {
          records(labels(i)) = records(labels(i)) :+ values(i)
        }
      }
    } finally {
      // Close the streams
      // This will be executed if the file has been opened regardless of whether or not there were any exceptions.
      fileIn.close()
      linesIn.close()
    }
  } catch {
    // Response to failed file opening.
    case e: FileNotFoundException => e.printStackTrace()

    // Response to unsuccessful reading
    case e: IOException => e.printStackTrace()

  }

}
