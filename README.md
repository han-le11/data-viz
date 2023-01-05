# Numerical visualization library
In general, this library can be used to visualize numerical data graphically. In a sense, its idea is like matplotlib library of Python; however, currently my library only support line graph.

Basic line graph has 1 to n straight lines with starting and ending points determined by the coordinates (x, y) in the given data. Adjacent points are connected with a straight line.

* The given data is read from a CSV file, which is given by the user. The CSV file should have column names (that can be used as category names) and corresponding values.

* The legend (the colors used) are included automatically in the graph. The user are able to name the axes and units of the axes.

* The coordinate axes are numbered in a way that there is not too much spacing between labels and labels are not drawn on top of each other.

* The user has the possibility to decide whether a grid will be drawn behind the graph. The grid is drawn with a faintly visible dotted line. The size of the grid tiling can be configurable by the user.

The library can be re-structured a bit to allow potential expandability. For example, adding other graph types such as basic histogram and pie diagram should be relatively simple.

# Directory structure
The core structure:
```
{
|- datasets       <- Datasets used for demo
|
|- src            <- Source code
|
|- test           <- Unit test
}
```

# Project document

Check the file _document.pdf_ for more details:

* **User interface**

    Introduction to how to use the program: How is it started? What can you do with it? What commands the user can give and how, etc.

    Explantion of an example use case: visualize Open, High, Low, and Close prices of silver in the last 30 days. This silver price dataset is downloaded from Kaggle.


* **Program structure**

    How the program is split into the main sub-parts? What are the relationships between the main classes? 

  UML class diagram is included in this part. Key methods in classes are introduced in the UML diagram.


* **Algorithms**

  How the program performs the main tasks:

  Drawing basic line diagram.

  Scaling from number (i.e., from the input data) to pixel.

  Setting up the ticks on x axis and y axis.

  Setting up the grids.


* **Testing**

  A description of how the program is tested, including unit test and GUI test.

  So far, all tests have been passed.










