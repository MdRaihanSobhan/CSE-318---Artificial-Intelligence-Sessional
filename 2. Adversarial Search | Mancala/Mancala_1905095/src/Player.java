import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Player {
    int human;
    int depth;
    int hrstc_method;

    int playerNo;

    public Player(int human) {
        this.human = human;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public int getHuman() {
        return human;
    }

    public void setHuman(int human) {
        this.human = human;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHrstc_method() {
        return hrstc_method;
    }

    public void setHrstc_method(int hrstc_method) {
        this.hrstc_method = hrstc_method;
    }

    public int getTurn(Playground pg, int player){
        if(human==1){
            Scanner scanner = new Scanner(System.in);
            int move = scanner.nextInt();
            if(!pg.checkTurn(player,move-1)) {
                System.out.println("Invalid Move");
                return -1;
            }
            return move-1;
        }
        else{
            AI_Move move = minimax_with_a_b_pruning(pg,player,depth,true,-100000000,100000000, hrstc_method);

            if(!pg.checkTurn(player,move.turn))
            {
                return getFirstValidMove(pg,player);
            }
            return move.turn;
        }
    }

    public int getFirstValidMove(Playground pg, int player){
        if(player==1){
            for(int i=0;i<pg.holes;i++){
                if(pg.board[i]>0) return i;
            }
            return -1;
        }
        else{
            for(int i=pg.goal_1+1; i< pg.goal_2; i++){
                if(pg.board[i]>0) return (i- pg.holes-1);
            }
            return -1;
        }
    }

    public AI_Move minimax_with_a_b_pruning(Playground pg, int player, int depth, boolean MaxPlayer, int alpha, int beta, int hr_method)
    {
        if( depth == 0) {
            int hrstc = pg.getHeuristic(hr_method,player,false, pg.getCaptured());
            return new AI_Move(hrstc,-1,false);
        }

        if(MaxPlayer) {

            AI_Move optimal_move = new AI_Move(-1000000,-1,false);

            Vector<Integer> possible_turns = new Vector<>();

            int maxmove = pg.holes-1;
            while (true){
                if(maxmove<0) break;
                possible_turns.add(maxmove);
                maxmove--;
            }
            Collections.shuffle(possible_turns);

            for(int hole=0;hole<6;hole++)
            {
                int iterator_hole = possible_turns.get(hole);

                int hole_no = iterator_hole;
                if(player == 2)
                {
                    hole_no = hole_no + pg.holes + 1;
                }

                if(pg.board[hole_no] == 0) continue;

                Playground demoPG = new Playground(pg);
                MaxPlayer = demoPG.giveTurn(player,iterator_hole);

                AI_Move new_move = minimax_with_a_b_pruning(demoPG,player,depth-1,MaxPlayer,alpha,beta,hr_method);

                if(new_move.heuristic > optimal_move.heuristic)
                {
                    optimal_move.heuristic = new_move.heuristic;
                    optimal_move.turn = iterator_hole;
                    optimal_move.additional_move = MaxPlayer;
                }

                alpha = Math.max(alpha,new_move.heuristic);

                if(beta <= alpha)
                    break;

            }
            return optimal_move;
        }
        else{

            AI_Move optimal_move = new AI_Move(1000000,-1,false);

            Vector<Integer> possible_turns = new Vector<>();
            int maxmove = pg.holes-1;
            while (true){
                if(maxmove<0) break;
                possible_turns.add(maxmove);
                maxmove--;
            }
            Collections.shuffle(possible_turns);

            for(int hole=0;hole<6;hole++)
            {
                int iterator_hole = possible_turns.get(hole);

                int hole_no = iterator_hole;
                if(player == 2)
                {
                    hole_no = hole_no + pg.holes + 1;
                }

                if(pg.board[hole_no] == 0) continue;

                Playground demoPG = new Playground(pg);
                MaxPlayer = demoPG.giveTurn(player,iterator_hole);

                AI_Move new_move = minimax_with_a_b_pruning(demoPG,player,depth-1,!MaxPlayer,alpha,beta,hr_method);

                if(new_move.heuristic < optimal_move.heuristic)
                {
                    optimal_move.heuristic = new_move.heuristic;
                    optimal_move.turn = iterator_hole;
                    optimal_move.additional_move = MaxPlayer;
                }

                beta = Math.min(beta,new_move.heuristic);

                if(beta <= alpha)
                    break;

            }
            return optimal_move;
        }
    }
}
