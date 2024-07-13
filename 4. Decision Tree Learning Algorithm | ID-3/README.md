# Decision Tree Algorithm - ID-3

This contains a Java implementation of the ID-3 algorithm for decision tree learning.

## Prerequisites

To run this implementation, you need to have Java installed on your machine.
You also need gnuplot to plot the graph.

## Running the code

1. Make sure you have car.data file in the project directory (parent directory of src).
2. Run main/Main.java to start the program. 
3. A csv file, "accuracy_metrics.csv" will be generated. 
4. To plot the graph, run the following command in the terminal of the project directory:
    gnuplot plot.plt. 
    This will generate a pdf, "graph.pdf" in the project directory.
5. Comment out line 60 of main/Main.java [ Collections.shuffle(dataset); ] and run the code again. Now, the dataset won't be shuffled before splitting into training and test sets. 
We usually Shuffle the dataset before splitting into training and test sets to avoid bias in the data set (if any) from affecting the results of the experiment as the data set is ordered and to ensure that the results are statistically significant 
