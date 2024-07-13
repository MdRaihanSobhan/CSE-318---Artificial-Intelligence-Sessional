import java.util.Comparator;

public class HammingComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        if (n1.getHammingDistance()+ n1.getCostSoFar() > n2.getHammingDistance()+ n2.getCostSoFar())
            return 1;
        else if (n1.getHammingDistance()+ n1.getCostSoFar() < n2.getHammingDistance()+ n2.getCostSoFar())
            return -1;
        return 0;
    }
}