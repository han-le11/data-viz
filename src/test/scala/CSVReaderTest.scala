//import org.scalatest.exceptions.TestFailedException
import org.scalatest.flatspec.AnyFlatSpec
import java.io.{FileNotFoundException, IOException}

class CSVReaderTest extends AnyFlatSpec {

  "The CSV reader" should "read the input file correctly." in {
    val file = "example/silver.csv"
    val reader = new CSVReader(file)
    val input = reader.records
    assert(input.keySet === Set("Date","Open","High","Low","Close","Volume","Currency"))
    assert(input("Open").take(10) === Vector("5.29", "5.17", "5.127", "5.15", "5.145",
            "5.195", "5.1", "5.11", "5.112", "5.127"))  // Check the first 10 elements
    assert(input("High").slice(10, 21) === Vector("5.135", "5.2", "5.222", "5.255", "5.277", "5.25",
      "5.382", "5.307", "5.287", "5.26", "5.21"))  // Check index 10 to 20
    assert(input("Low").takeRight(10) === Vector("25.46", "25.855", "25.775", "25.805",
      "25.165", "24.98", "24.48", "24.08", "24.133", "23.422"))  // Check the last 10 elements
    assert(input("Close").take(10) === Vector("5.335","5.17", "5.127", "5.15", "5.145",
            "5.15", "5.1", "5.115", "5.112", "5.127"))
  }

  "The CSV reader" should "throw exception if it loads a file that does not exist."  in {
    assertThrows[FileNotFoundException] {
      val file = "example/.csv"
      val reader = new CSVReader(file)
    }
  }

  "The CSV reader" should "throw exception if there is an invalid file format." in {
    val file = "example/invalid_format.csv"
    assertThrows[InvalidCsvException] {
      val reader = new CSVReader(file)
    }
  }

}