package decison_tree;

import compute.Calculation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Decision_Tree {

    private String findBestAttributeToSplit(List<CarExamples> data, Set<String> attributes) {
        String bestAttribute = null;
        double maxInformationGain = Double.NEGATIVE_INFINITY;

        for (String attribute : attributes) {
            double informationGain = Calculation.computeInfoGain(data, attribute);
            if (informationGain > maxInformationGain) {
                maxInformationGain = informationGain;
                bestAttribute = attribute;
            }
        }

        return bestAttribute;
    }

    // This method is used to split the data into subsets based on the value of a given attribute
    public static Map<String, List<CarExamples>> split(List<CarExamples> subset, String attribute) {
        // 1. group by attribute value
        // 2. for each attribute value, get the list of instances
        // 3. put the attribute value and the list of instances into a map
        return subset.stream()
                .collect(Collectors.groupingBy(instance -> instance.getAttribute(attribute)));
    }

    // This method is used to find the plurality class label in a list of instances
    public static String getPlurality(List<CarExamples> examplesList) {
        // 1. group by class label
        // 2. for each class label, get the list of instances
        // 3. put the class label and the list of instances into a map

        Map<String, Long> classCounts = examplesList.stream()
                .collect(Collectors.groupingBy(CarExamples::getLabel, Collectors.counting()));

        String pluralityClass = null;
        long maxCount = 0;

        for (String label : classCounts.keySet()) {
            long count = classCounts.get(label);
            if (count > maxCount) {
                maxCount = count;
                pluralityClass = label;
            }
        }

        return pluralityClass;
    }

    public DT_Node learnDecisionTree(List<CarExamples> carExamplesList, Set<String> attributes, List<CarExamples> parentExampleList ) {
        // case 1: no example left , return the plurality class label of the parent node
        if (carExamplesList.isEmpty()) {
            return new DT_Node(getPlurality(parentExampleList), carExamplesList);
        }

        // case 2: all examples have the same class label, return the class label
        if (carExamplesList.stream().map(CarExamples::getLabel).distinct().count() == 1) {
            DT_Node leaf = new DT_Node(null, carExamplesList); // Initialize with null class label
            leaf.setNode_label(carExamplesList.get(0).getLabel()); // Set the class label for leaf node
            return leaf;
        }

        // case 3: no attributes left, return the plurality class label of the examples
        if (attributes.isEmpty()) {
            DT_Node leaf = new DT_Node(null, carExamplesList); // Initialize with null class label
            leaf.setNode_label(getPlurality(carExamplesList)); // Set the class label for leaf node
            return leaf;
        }

        // case 4: normal case , recursively build the decision tree
        String bestAttribute = findBestAttributeToSplit(carExamplesList, attributes);

        DT_Node node = new DT_Node(bestAttribute, carExamplesList);

        Set<String> remainingAttributes = new HashSet<>(attributes);
        remainingAttributes.remove(bestAttribute);

        Map<String, List<CarExamples>> attribute_subset_mapping = split(carExamplesList, bestAttribute);
        for (String value : attribute_subset_mapping.keySet()) {
            List<CarExamples> subset = attribute_subset_mapping.get(value);
            DT_Node childNode = learnDecisionTree(subset, remainingAttributes, carExamplesList);
            node.getAttribute_Child_map().put(value, childNode);
        }

        return node;
    }

}
