package compute;

import decison_tree.CarExamples;
import decison_tree.DT_Node;
import decison_tree.Decision_Tree;

import java.util.List;

public class Evaluation {
    // This method is used to find the class label of an instance by traversing the decision tree
    private static String find_label_by_traversing_DT(CarExamples instance, DT_Node dtNode) {

        if (dtNode.getNode_label() != null) { // Leaf node
            return dtNode.getNode_label(); // Return the class label
        }

        // Get the attribute value of the instance for the attribute of the node
        String attributeValue = instance.getAttribute(dtNode.getAttribute());

        // Get the next node in the decision tree
        DT_Node nextDtNode = dtNode.getAttribute_Child_map().get(attributeValue);

        // If no matching child node, return the majority class label of the parent node
        if (nextDtNode == null) {
            String label = Decision_Tree.getPlurality(dtNode.getCarExamples()); // Get the majority class label of the parent node
            return label; // Return the majority class label
        }

        return find_label_by_traversing_DT(instance, nextDtNode); // Recursively traverse the decision tree

    }

// this method is used to evaluate the accuracy of the decision tree on the test set
    public static double testLearning(List<CarExamples> testSet, DT_Node tree) {
        int correct_count = 0;
        for (CarExamples instance : testSet) {

            String learnedLabel = find_label_by_traversing_DT(instance, tree);

            String actualLabel = instance.getLabel();

            if (learnedLabel != null && actualLabel != null && learnedLabel.equals(actualLabel)) {
                correct_count++;
            }
        }

        return (double) correct_count / testSet.size();
    }

}
