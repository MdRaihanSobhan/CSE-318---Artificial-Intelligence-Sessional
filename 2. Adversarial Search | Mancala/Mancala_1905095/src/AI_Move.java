public class AI_Move {
    int turn;
    int heuristic;
    boolean additional_move;

    public AI_Move( int heuristic, int turn, boolean additional_move) {
        this.turn = turn;
        this.heuristic = heuristic;
        this.additional_move = additional_move;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public boolean isAdditional_move() {
        return additional_move;
    }

    public void setAdditional_move(boolean additional_move) {
        this.additional_move = additional_move;
    }
}

