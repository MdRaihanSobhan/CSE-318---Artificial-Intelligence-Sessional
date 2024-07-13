// Java program to demonstrate working of
// comparator based priority queue constructor
import java.util.*;

public class Main {
    public static void printBoard(int[][] demoBoard, int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(demoBoard[i][j]==0){
                    System.out.print("* "); //null block
                    continue;
                }
                System.out.print(demoBoard[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean equalsBoard(int[][] firstBoard, int[][] secondBoard, int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(firstBoard[i][j]!=secondBoard[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void printOutput(Node demoNode){
        Node temp = demoNode;
        Stack<Node> nodeStack = new Stack<>(); //stack to store the nodes

        while (temp!=null){ //pushing the nodes in the stack
            nodeStack.push(temp);
            temp= temp.getParent();
        }
        while (!nodeStack.isEmpty()){ //popping the nodes from the stack
            temp=nodeStack.pop();
            printBoard(temp.getBoard(), temp.getSize());
        }
    }
    public static boolean isSolvable(int[][] demoBoard, int n){ //check if the board is solvable
        int[] ara = new int[n*n-1]; //array to store the board
        int k=0; //index of the array
        int rowdistance=0; //distance of the null block from the bottom row
        for(int i=0; i<n; i++){ //storing the board in the array
            for(int j=0; j<n; j++){
                if(demoBoard[i][j]==0){ //if null block
                    rowdistance= n-1-i; //distance of the null block from the bottom row
                    continue;
                }
                ara[k] = demoBoard[i][j]; //storing the board in the array
                k++;
            }
        }
        int inversions= 0;
        for(int i=0; i<n*n-1; i++){
            k= ara[i];
            if(ara[i]==0) continue;
            for(int j=i+1; j<n*n-1; j++){
                if(ara[j]<ara[i]) inversions++;
            }
        }
        if(n%2==1){
            if(inversions%2==0){
                return true;
            }
            else {
                System.out.println("Inversion Count is odd: "+ inversions);
                return false;
            }
        }
        else {
            if((inversions+rowdistance)%2==0){
                return true;
            }
            else {
                System.out.println("Inversion Count + row distance of null is odd: "+inversions+" + "+rowdistance+" = "+ (inversions+rowdistance));
                return false;
            }
        }
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        PriorityQueue<Node> pq_manhattan = new
                PriorityQueue<Node>(2, new ManhattanComparator());
        PriorityQueue<Node> pq_hamming = new
                PriorityQueue<Node>(2, new HammingComparator());

        System.out.println("Enter the size: ");
        int n = scanner.nextInt();
        int[][] inputBoard = new int[n][n];
        System.out.println("Enter the board: ");
        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                inputBoard[i][j] = scanner.nextInt();
            }
        }
        if(isSolvable(inputBoard,n)==false){
            System.out.println("Unsolvable Puzzle");
            return;
        }
        Node node1 = new Node(n,inputBoard,null,0);
        pq_manhattan.add(node1);
        pq_hamming.add(node1);
        /*
        Node nodes[] = node1.getChildren();
        System.out.println(nodes.length);
        for(int i=0;i<nodes.length;i++){
            printBoard(nodes[i].getBoard(),n);
        }
        */


        System.out.println("Using manhattan distance as heuristic");

        int it=100;
        int explored =1;
        int expanded = 0;
        while (!pq_manhattan.isEmpty()){
            it--;
            Node move = pq_manhattan.poll();
            expanded++;
            // System.out.println("Dequeue following");
            // printBoard(move.getBoard(),n);
            if(move.equalsGoal()){
                // printBoard(move.getBoard(), n);
                System.out.println("Minimum number of moves = "+ move.getCostSoFar());
                printOutput(move);
                break;
            }
            Node nodes[]= move.getChildren();
            int reached =0;
            for(int i=0; i<nodes.length; i++){
                if(nodes[i].equalsGoal()){
                    reached=1;
                    // System.out.println("Done");
                    // printBoard(nodes[i].getBoard(), n);
                    System.out.println("Minimum number of moves = "+nodes[i].getCostSoFar());
                    printOutput(nodes[i]);
                    break;
                }
                if(nodes[i].getParent().getParent()!=null){
                    if(equalsBoard(nodes[i].getBoard(), nodes[i].getParent().getParent().getBoard(),n)==true){
                        continue;
                    }
                }
                pq_manhattan.add(nodes[i]);
                explored++;
                // printBoard(nodes[i].getBoard(), n);
            }
            if(reached==1){
                break;
            }
        }
        System.out.println("expanded: "+ expanded);
        System.out.println("explored: "+ explored);

        expanded=0;
        explored=1;

        System.out.println("Using hamming distance as heuristic");


        it=100;
        while (!pq_hamming.isEmpty() ){
            it--;
            Node move = pq_hamming.poll();
            expanded++;
          //  System.out.println("Dequeue following");
          //  printBoard(move.getBoard(),n);
            if(move.equalsGoal()){
               // printBoard(move.getBoard(), n);
                System.out.println("Minimum number of moves = "+ move.getCostSoFar());
                printOutput(move);
                break;
            }
            Node nodes[]= move.getChildren();
            int reached =0;
            for(int i=0; i<nodes.length; i++){
                if(nodes[i].equalsGoal()){
                    reached=1;
                    // System.out.println("Done");
                    // printBoard(nodes[i].getBoard(), n);
                    System.out.println("Minimum number of moves = "+nodes[i].getCostSoFar());
                    printOutput(nodes[i]);
                    break;
                }
                if(nodes[i].getParent().getParent()!=null){
                    if(equalsBoard(nodes[i].getBoard(), nodes[i].getParent().getParent().getBoard(),n)==true){
                        continue;
                    }
                }
                pq_hamming.add(nodes[i]);
                explored++;
               // printBoard(nodes[i].getBoard(), n);
            }
            if(reached==1){
                break;
            }
        }
        System.out.println("expanded: "+ expanded);
        System.out.println("explored: "+ explored);
    }
}



