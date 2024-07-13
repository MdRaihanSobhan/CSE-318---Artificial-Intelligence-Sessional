package main;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


import compute.Calculation;
import compute.Evaluation;
import decison_tree.CarExamples;
import decison_tree.DT_Node;
import decison_tree.Decision_Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class Main {
// this method is used to load the dataset from the file
    public static List<CarExamples> getInputDataSet(String filePath) throws IOException {
        List<CarExamples> carDataSet = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] attributes = line.split(",");
            if (attributes.length == 7) {
                CarExamples instance = new CarExamples(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6]);
                carDataSet.add(instance);
            }
        }

        reader.close();
        return carDataSet;
    }

    public static void main(String[] args) throws IOException {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]{"Training Data Percentage", "Mean Accuracy", "Standard Deviation of Accuracy"});

        List<CarExamples> dataset = getInputDataSet("car.data");
        String outputFilePath = "accuracy_metrics.csv";

        Set<String> attributes = Set.of("buying", "maint", "doors", "persons", "lugBoot", "safety");

        int numExperiments = 20;
        double percentage = 0.01; // 80% for training

        List<Double> accuracies = new ArrayList<>();
        while (percentage<=0.999){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            for (int i = 0; i < numExperiments; i++) {
                // Shuffle the dataset before splitting into training and test sets to avoid bias in the data set (if any)
                // from affecting the results of the experiment as the data set is ordered
                // and to ensure that the results are statistically significant
                Collections.shuffle(dataset);
                // Split the dataset into training and test sets
                List<CarExamples> trainingSet = dataset.subList(0, (int) (percentage * dataset.size()));
//                List<CarAttributes> testSet = dataset.subList(0, (int) (percentage * dataset.size()));
                List<CarExamples> testSet = dataset.subList((int) (percentage * dataset.size()), dataset.size());
                Decision_Tree decisionTree = new Decision_Tree();
                // Learn the decision tree using the training set and the attributes
                DT_Node learnedTree = decisionTree.learnDecisionTree(trainingSet, attributes, new ArrayList<>());

                // Evaluate the accuracy of the learned tree using the test set
                double accuracy = Evaluation.testLearning(testSet, learnedTree); // Pass the learned tree for evaluation
                accuracies.add(accuracy);

            }


            double meanAccuracy = Calculation.computeMeanAccuracy(accuracies);
            double standardDeviationOfAccuracy = Calculation.computeSD_of_Accuracy(accuracies);

            dataLines.add(new String[]{decimalFormat.format(percentage*100), String.valueOf(meanAccuracy), String.valueOf(standardDeviationOfAccuracy)});

            try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
                for (String[] line : dataLines) {
                    fileWriter.append(String.join(",", line));
                    fileWriter.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            percentage = percentage + (0.01);
            accuracies.clear();

        }
        System.out.println("Output Accuracy metrics have been written to " + outputFilePath);

    }


}