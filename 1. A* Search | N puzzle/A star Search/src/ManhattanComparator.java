import java.util.Comparator;

public class ManhattanComparator implements Comparator<Node> {


    public int compare(Node n1, Node n2) {
        if (n1.getManhattanDistance()+ n1.getCostSoFar() > n2.getManhattanDistance() + n2.getCostSoFar())
            return 1;
        else if (n1.getManhattanDistance() + n1.getCostSoFar() < n2.getManhattanDistance()+ n2.getCostSoFar())
            return -1;
        return 0;
    }
}