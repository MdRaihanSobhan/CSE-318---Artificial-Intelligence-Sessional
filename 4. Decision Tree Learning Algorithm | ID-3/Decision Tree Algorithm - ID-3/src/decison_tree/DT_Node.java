package decison_tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DT_Node {
    private String attribute;
    private Map<String, DT_Node> attribute_Child_map; // Map of attribute values to child nodes
    private String node_label;

    private List<CarExamples> carExamples;



    public DT_Node(String attribute, List<CarExamples> data) {
        this.attribute = attribute;
        this.attribute_Child_map = new HashMap<>();
        this.node_label = null;
        this.carExamples = data;
    }


    public String getAttribute() {
        return attribute;
    }

    public Map<String, DT_Node> getAttribute_Child_map() {
        return attribute_Child_map;
    }

    public String getNode_label() {
        return node_label;
    }

    public void setNode_label(String node_label) {
        this.node_label = node_label;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setAttribute_Child_map(Map<String, DT_Node> attribute_Child_map) {
        this.attribute_Child_map = attribute_Child_map;
    }

    public List<CarExamples> getCarExamples() {
        return carExamples;
    }

    public void setCarExamples(List<CarExamples> carExamples) {
        this.carExamples = carExamples;
    }
}
