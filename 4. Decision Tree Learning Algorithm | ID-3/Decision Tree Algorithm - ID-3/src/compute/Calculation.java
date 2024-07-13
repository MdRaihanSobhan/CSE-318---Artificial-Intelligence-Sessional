package compute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import decison_tree.CarExamples;
import decison_tree.Decision_Tree;
public class Calculation {
    private static double computeEntropy(List<CarExamples> subset_of_examples) {
        int total_examples = subset_of_examples.size();
        Map<String, Long> exampleCounts = subset_of_examples.stream()
                .collect(Collectors.groupingBy(CarExamples::getLabel, Collectors.counting()));
        // Map<Label, Count> , e.g. Map<unacc, 1210>

        double resultingEntropy = 0.0;
        for (String label : exampleCounts.keySet()) {
            double probability = (double) exampleCounts.get(label) / total_examples;
            double logProbability = Math.log(probability);
            resultingEntropy-= probability * logProbability ;
        }

        return resultingEntropy;
    }

    public static double computeInfoGain(List<CarExamples> subset_of_examples, String attribute) {
        double entropy_Before_Split = computeEntropy(subset_of_examples); // H(S)

        Map<String, List<CarExamples>> attribute_Subset_Map = Decision_Tree.split(subset_of_examples, attribute);

        double entropy_After_Split = 0.0;
        for (String value : attribute_Subset_Map.keySet()) {
            List<CarExamples> subsetForValue = attribute_Subset_Map.get(value); // Sv
            double weight = (double) subsetForValue.size() / subset_of_examples.size(); // |Sv| / |S|
            entropy_After_Split += weight * computeEntropy(subsetForValue); // H(S, A)
        }

        return entropy_Before_Split - entropy_After_Split; // H(S) - H(S, A)
    }

    public static double computeMeanAccuracy(List<Double> list) {
        double sum = 0.0;
        for (double list_entry : list) {
            sum += list_entry;
        }
        return sum / list.size();
    }

    public static double computeSD_of_Accuracy(List<Double> list) {
        double mean = computeMeanAccuracy(list);
        double sum_of_diff_squares = 0.0;
        for (double list_entry : list) {
            double subtracted = list_entry - mean;
            sum_of_diff_squares += subtracted * subtracted;
        }
        double returnValue = Math.sqrt(sum_of_diff_squares / list.size());
        return returnValue;
    }
}
