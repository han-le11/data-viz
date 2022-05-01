import java.io._
import scala.collection.mutable

/** Read an input CSV file */

class CSVReader(val inputFile: String) {

  // Record the column names as keys and data of columns as values
  var records = new mutable.LinkedHashMap[String, Vector[String]]

  try {
    val fileIn = new FileReader(inputFile)
    val linesIn = new BufferedReader(fileIn)

    // open the streams
    try {
      // read the first line which contains the names of columns and use them as labels
      val labels = linesIn.readLine().split(",")

      for (i <- 0 until labels.size) {
        records += (labels(i) -> Vector[String]())  // add the labels and their corresponding empty vectors
      }

      var oneLine: String = null
      while ({oneLine = linesIn.readLine(); oneLine != null}) {
        val values = oneLine.split(",")  // use comma as separator

        if (values.size != labels.size) {
          throw new InvalidCsvException("Invalid file format. The amount of values does not match the number of the columns. " +
            "There might be missing values or redundant values.")
        }

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
  }
}

case class InvalidCsvException(message: String) extends Exception(message) {

}
