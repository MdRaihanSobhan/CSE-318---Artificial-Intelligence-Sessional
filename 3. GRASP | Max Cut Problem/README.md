# GRASP | Max Cut Problem

## Have a glance at the [`assignment specification`](/3.%20GRASP%20|%20Max%20Cut%20Problem/Assignment_03_Max_Cut.pdf)

## How to run 
- The code is implemented in c++.
- Give the following commands in the terminal
    - At first, go to the project directory 1905095_GRASP
        ```sh
        cd 1905095_GRASP

    - Then run the shell file using bash. It will run for all the 54 input data files and generate the output statistics in 1905095_output.csv file. As the dataset is huge, it will take time to run depending on the system configuration.
        ```sh
        bash run.sh

    - To run the code for a single input file, for example g1.rud, run the following command. Just 1 line output will be generated in the 1905095_output.csv file corresponding to that input. 
        ```sh
        g++ 1905095.cpp -o 1905095.out 
        ./1905095.out<"set1/g1.rud">>"1905095_output.csv"

## Resources
- Read some articles to learn about GRASP from the [`resources`](/3.%20GRASP%20|%20Max%20Cut%20Problem/Resources/) directory.

## Report
- Read the [`report`](/3.%20GRASP%20|%20Max%20Cut%20Problem/Report.pdf) for detailed explanation of the implementation and results.