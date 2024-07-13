public class Playground {
    int board[];
    int holes = 6;
    int total_holes = 14;
    int seeds = 4;
    int goal_1 = 6;
    int goal_2 = 13;

    int captured=0;

    public Playground() {
        this.board = new int[total_holes];
        for(int i=0; i<total_holes; i++){
            this.board[i]= seeds;
            if(i==goal_1 || i==goal_2){
                this.board[i]=0;
            }
        }
        showGround();
    }

    public Playground(Playground pg){
        this.holes = pg.holes;
        this.goal_1 = pg.goal_1;
        this.goal_2 = pg.goal_2;
        this.total_holes = pg.total_holes;
        this.seeds = pg.seeds;
        this.board = new int[total_holes];
        for(int i=0; i<total_holes; i++){
            this.board[i] = pg.board[i];
        }
    }

    public int getCaptured() {
        return captured;
    }

    public void setCaptured(int captured) {
        this.captured = captured;
    }

    public boolean giveTurn(int player, int holeNo){
        if(player==1){
            int seedcount = this.board[holeNo];
            int curr_hole = holeNo + 1;

            while (seedcount>0){
                if(curr_hole==goal_2){
                    curr_hole=0;
                    continue;
                }
                this.board[curr_hole]++;
                this.board[holeNo]--;
                seedcount--;
                if(seedcount==0){
                    if(curr_hole==goal_1){
                        return true;
                    }
                    int ulta_hole = (total_holes-2) - curr_hole;
                    if(this.board[curr_hole]==1 && this.board[ulta_hole]>0 && curr_hole<holes && curr_hole>=0){
                        this.board[goal_1]+= this.board[curr_hole];
                        this.board[goal_1]+= this.board[ulta_hole];
                        int stonesCaptured = (this.board[ulta_hole]+this.board[curr_hole]);
                        this.setCaptured(stonesCaptured);
                        this.board[curr_hole]=0;
                        this.board[ulta_hole]=0;
                    }

                }
                curr_hole++;
                curr_hole%=total_holes;
            }
        }
        else if(player == 2){
            holeNo+= (holes+1);
            int seedcount = this.board[holeNo];
            int curr_hole = holeNo + 1;

            while (seedcount>0){
                if(curr_hole==goal_1){
                    curr_hole++;
                    curr_hole%=total_holes;
                    continue;
                }
                this.board[curr_hole]++;
                this.board[holeNo]--;
                seedcount--;
                if(seedcount==0){
                    if(curr_hole==goal_2){
                        return true;
                    }
                    int ulta_hole = (total_holes-2) - curr_hole;
                    if(this.board[curr_hole]==1 && this.board[ulta_hole]>0 && curr_hole<goal_2 && curr_hole>goal_1){
                        this.board[goal_2]+= this.board[curr_hole];
                        this.board[goal_2]+= this.board[ulta_hole];
                        int stonesCaptured = (this.board[ulta_hole]+this.board[curr_hole]);
                        this.setCaptured(stonesCaptured);
                        this.board[curr_hole]=0;
                        this.board[ulta_hole]=0;
                    }

                }
                curr_hole++;
                curr_hole%=total_holes;
            }
        }
        return false;
    }

    public boolean checkTurn(int player, int holeNo){
        if(holeNo>=holes || holeNo<0){
            return false;
        }
        if(player==2){
            holeNo+= (holes+1);
        }
        if(this.board[holeNo]<1) return false;
        return true;
    }

    public boolean checkOver(){
        boolean check = true;
        for(int i=0; i<goal_1; i++){
            if(this.board[i]>0) check = false;
        }
        if(check==true){
            for(int i=goal_1+1; i<total_holes-1; i++ ){
                this.board[goal_2]+=this.board[i];
                this.board[i]=0;
            }
            return true;
        }
        check = true;
        for(int i=goal_1+1; i<total_holes-1; i++){
            if(this.board[i]>0) check = false;
        }
        if(check==true){
            for(int i=0; i<goal_1; i++){
                this.board[goal_1]+=this.board[i];
                this.board[i]=0;
            }
            return true;
        }
        return false;
    }

    public int getWinner(){
        if(this.board[goal_1]>this.board[goal_2]) return 1;
        else if(this.board[goal_1]< this.board[goal_2]) return 2;
        else return 0; // draw
    }

    public void showGround(){
        System.out.print("\t\t");
        for(int i=total_holes-2; i>goal_1; i--){
            System.out.print(board[i]+ " ");
        }
        System.out.println();
        System.out.println(board[goal_2] + "\t\t\t\t\t\t" + board[goal_1]);
        System.out.print("\t\t");
        for(int i=0; i<goal_1; i++){
            System.out.print(board[i]+ " ");
        }
        System.out.println();
    }


    public int h1(int player){
        int h = getStorageCount(1) - getStorageCount(2);
        if(player == 2) h*=-1;
        return h;
    }

    public int h2(int player){
        int a = getStorageCount(1) - getStorageCount(2);
        int b = getSeedsResiding(1) - getSeedsResiding(2);
        if(player == 2){
            a*=-1; b*=-1;
        }
        return 5*a + 7*b;
    }

    public int h3(int player, boolean additional_move){
        if(additional_move == true){
            return h2(player) + 9;
        }
        else {
            return h2(player);
        }
    }

    public int h4(int player, boolean additional_move, int stones_captured){
        if(stones_captured>0){
            return h3(player,additional_move)+ stones_captured*11;
        }
        else {
            return h3(player,additional_move);
        }
    }
    public int getHeuristic(int h, int player, boolean additional_move, int stones_captured){
        if(h==1) return h1(player);
        else if(h==2) return h2(player);
        else if(h==3) return h3(player, additional_move);
        else if(h==4) return h4(player,additional_move, stones_captured);
        return -1;
    }


    public int getStorageCount(int player){
        if(player==1) return this.board[goal_1];
        else if(player==2) return this.board[goal_2];
        else return -1;
    }
    public int getSeedsResiding(int player){
        int count = 0;
        if(player==1){
            for(int i=0; i<holes; i++){
                count+=this.board[i];
            }
        }
        else{
            for(int i=goal_1+1; i<total_holes; i++){
                count+=this.board[i];
            }
        }
        return count;
    }

}
