import java.util.Arrays;
import java.util.zip.ZipError;

import static java.lang.Math.abs;
import static java.lang.Math.cos;

class Node {

    int size;
    public Node parent;
    public int[][] board;

    public boolean goalReached;
    public int[][] goalBoard;

    int costSoFar;
    int manhattanCost;
    int hammingCost;

    public int[][] getBoard() {
        return board;
    }

    public void setGoalBoard(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                this.goalBoard[i][j]= i*n + j + 1;
            }
        }
        this.goalBoard[n-1][n-1]=0;
    }
    private void swapBlocks(int[][] tempBoard, Coordinate c1, Coordinate c2) {
        int temp = tempBoard[c1.x][c1.y];
        tempBoard[c1.x][c1.y] = tempBoard[c2.x][c2.y];
        tempBoard[c2.x][c2.y]= temp;
    }
    public int getHammingDistance(){
        int d=0;
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if(i==this.size-1 && j==this.size-1) break;
                if(this.board[i][j]!=this.goalBoard[i][j]) d++;
            }
        }
        return d;
    }

    //manhattan distance
    public int getManhattanDistance(){
        int row=0; //row of the block in the goal board
        int col=0; //column of the block in the goal board
        int d=0; // manhattan distance
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if(this.board[i][j]==0) continue; //null block
                if(this.board[i][j]%this.size==0){ //last column
                    row = this.board[i][j]/this.size -1; //row of the block in the goal board
                    col = this.size-1; //column of the block in the goal board
                }
                else { //not last column
                    row = this.board[i][j]/this.size; //row of the block in the goal board
                    col = this.board[i][j]%this.size-1; //column of the block in the goal board
                }
                d+= abs(row-(i));   // row distance
                d+= abs(col-(j));  // column distance
            }
        }
        return d;
    }

    //create a child node
    private void createChild(Node[] children, int id, Coordinate c1, Coordinate c2) {
        int[][] tempBoard = new int[this.size][this.size];
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                tempBoard[i][j]= this.board[i][j];
            }
        }
        swapBlocks(tempBoard, c1, c2); //swap the blocks
        children[id] = new Node(this.size, tempBoard, this,this.costSoFar+1);
        //create a new node
    }


    public Node[] getChildren(){
        int childcount;
        int zeroRow=-1, zeroCol=-1;
        //find the null block
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size;j++){
                if(this.board[i][j]==0){
                    zeroRow=i;
                    zeroCol=j;
                    break;
                }
            }
            if(zeroRow!=-1) break;
        }

        //find the number of children
        if((zeroRow==0 && (zeroCol==0 || zeroCol==this.size-1)) ||
                (zeroRow==this.size-1 && (zeroCol==0 || zeroCol== this.size-1))){
            childcount=2; //corner block
        }
        else if(zeroRow==0 || zeroRow==this.size-1 || zeroCol==0 || zeroCol==this.size-1){
            childcount=3; //edge block
        }
        else{
            childcount=4; //middle block
        }


        Node nodes[]= new Node[childcount];

        int id=0;
        Coordinate zero = new Coordinate(zeroRow,zeroCol); //coordinate of the null block

        if(childcount==2){ //corner block
            Coordinate c1,c2;
            if(zeroRow==0){ //top
                c1= new Coordinate(zeroRow+1, zeroCol);
                if(zeroCol==0){
                    c2= new Coordinate(zeroRow, zeroCol+1 ); //right
                }
                else{
                    c2= new Coordinate(zeroRow, zeroCol-1); //left
                }
            }
            else{ //bottom
                c1= new Coordinate(zeroRow-1, zeroCol);
                if(zeroCol==0){
                    c2= new Coordinate(zeroRow, zeroCol+1 ); //right
                }
                else{
                    c2= new Coordinate(zeroRow, zeroCol-1); //left
                }

            }

            createChild(nodes, id, zero, c1);
            id+=1;
            createChild(nodes, id, zero, c2);



        }
        else{
            Coordinate c1 = new Coordinate(zeroRow+1, zeroCol);     //bottom
            Coordinate c2 = new Coordinate(zeroRow-1, zeroCol);    //top
            Coordinate c3 = new Coordinate(zeroRow, zeroCol+1);     //right
            Coordinate c4 = new Coordinate(zeroRow, zeroCol-1);     //left
            if(childcount==3){  //edge block
                if(zeroRow==0){ //top
                    createChild(nodes, id++, zero, c1);     //bottom
                    createChild(nodes, id++, zero, c3);    //right
                    createChild(nodes, id, zero, c4);   //left
                }
                else if(zeroRow==this.size-1){  //bottom
                    createChild(nodes, id++, zero, c2);    //top
                    createChild(nodes, id++, zero, c3);   //right
                    createChild(nodes, id, zero, c4);   //left
                }
                else if(zeroCol==0){ //left
                    createChild(nodes, id++, zero, c1);     //bottom
                    createChild(nodes, id++, zero, c2);     //top
                    createChild(nodes, id, zero, c3);       //right
                }
                else if(zeroCol==this.size-1){  //right
                    createChild(nodes, id++, zero, c1);     //bottom
                    createChild(nodes, id++, zero, c2);     //top
                    createChild(nodes, id, zero, c4);      //left
                }
            }
            else{ //middle block
                createChild(nodes, id++, zero, c1);     //bottom
                createChild(nodes, id++, zero, c2);     //top
                createChild(nodes, id++, zero, c3);     //right
                createChild(nodes, id, zero, c4);       //left
            }
        }

        return nodes;

    }
//
    public boolean equalsGoal(){    //check if the board is equal to the goal board
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if(this.board[i][j]!= this.goalBoard[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public Node(int n, int[][] board, Node parent, int costSoFar) {
        this.size = n;
        this.board= board;
        this.goalBoard = new int[n][n];
        this.parent = parent;
        setGoalBoard(n);
        this.goalReached= true;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(this.board[i][j]!=this.goalBoard[i][j]){
                    this.goalReached= false;
                    break;
                }
            }
        }
        this.manhattanCost= getManhattanDistance();
        this.hammingCost = getHammingDistance();
        this.costSoFar = costSoFar;
    }

    public int getSize() {
        return size;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isGoalReached() {
        return goalReached;
    }

    public int[][] getGoalBoard() {
        return goalBoard;
    }

    public int getCostSoFar() {
        return costSoFar;
    }


}
