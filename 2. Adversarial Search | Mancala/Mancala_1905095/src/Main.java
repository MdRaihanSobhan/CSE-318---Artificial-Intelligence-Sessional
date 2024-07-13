import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Playground pg = new Playground();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Game Mode");
        System.out.println("1. Human vs Human \t 2. Human vs AI \t 3. AI vs AI");
        int mode = scanner.nextInt();
        if(mode==1){
            while (true){
                if(pg.checkOver()){
                    System.out.println("-------Game Over-----");
                    pg.showGround();
                    if(pg.getWinner()==1){
                        System.out.println("Player 1 won");
                        break;
                    }
                    else if(pg.getWinner()==2){
                        System.out.println("Player 2 won");
                        break;
                    }
                    else {
                        System.out.println("Match Drawn");
                        break;
                    }
                }
                System.out.println("Player 1's turn");
                int move;
                while (true){
                    move = scanner.nextInt();
                    if(pg.checkTurn(1, move-1)==false){
                        System.out.println("Invalid Move, try again");
                    }
                    else break;
                }

                while (pg.giveTurn(1, move-1) && pg.checkOver()==false){
                    if(pg.checkOver()) break;
                    pg.showGround();
                    System.out.println("Additional turn for player 1");
                    while (true){
                        move = scanner.nextInt();
                        if(pg.checkTurn(1, move-1)==false){
                            System.out.println("Invalid Move, try again");
                        }
                        else break;
                    }
                }
                pg.showGround();
                if(pg.checkOver()){
                    continue;
                }
                System.out.println("Player 2's turn");
                while (true){
                    move = scanner.nextInt();
                    if(pg.checkTurn(2, move-1)==false){
                        System.out.println("Invalid Move, try again");
                    }
                    else break;
                }

                while (pg.giveTurn(2, move-1) && pg.checkOver()==false){
                    if(pg.checkOver()) break;
                    pg.showGround();
                    System.out.println("Additional turn for player 2");
                    while (true){
                        move = scanner.nextInt();
                        if(pg.checkTurn(2, move-1)==false){
                            System.out.println("Invalid Move, try again");
                        }
                        else break;
                    }
                }
                pg.showGround();
            }
        }
        else if (mode == 2) {
            Player p1 = new Player(1);
            Player p2 = new Player(0);
            p2.setDepth(30);
            p2.setHrstc_method(2);
            int curr_player  = 1;
            while (true){
                pg.showGround();
                if(pg.checkOver()){
                    System.out.println("-------Game Over-----");
                    pg.showGround();
                    if(pg.getWinner()==1){
                        System.out.println("Player 1 won");
                        break;
                    }
                    else if(pg.getWinner()==2){
                        System.out.println("Player 2 won");
                        break;
                    }
                    else {
                        System.out.println("Match Drawn");
                        break;
                    }
                }
                int move;
                System.out.println("Player "+curr_player+"'s turn");
                if(curr_player==1){
                    move = p1.getTurn(pg, curr_player);
                }
                else if(curr_player==2){
                    move=p2.getTurn(pg, curr_player);
                }
                else continue;

                if(move==-1){
                    continue;
                }
                if(pg.giveTurn(curr_player, move)){
                    System.out.println("Additional Move for player "+ curr_player);
                    continue;
                }
                if(curr_player==1) curr_player=2;
                else if(curr_player==2) curr_player=1;
            }
        }
        else if (mode==3){
            Player p1 = new Player(0);
            System.out.println("Enter depth of Player 1: ");
            int d = scanner.nextInt();
            p1.setDepth(d);
            System.out.println("Enter Heruristic for Player 1: ");
            d = scanner.nextInt();
            p1.setHrstc_method(d);

            Player p2 = new Player(0);

            System.out.println("Enter depth of Player 2: ");
            d = scanner.nextInt();
            p2.setDepth(d);
            System.out.println("Enter Heruristic for Player 2: ");
            d = scanner.nextInt();
            p2.setHrstc_method(d);
            int curr_player  = 1;
            while (true){
                if(pg.checkOver()){
                    System.out.println("-------Game Over-----");
                    pg.showGround();
                    if(pg.getWinner()==1){
                        System.out.println("Player 1 won");
                        break;
                    }
                    else if(pg.getWinner()==2){
                        System.out.println("Player 2 won");
                        break;
                    }
                    else {
                        System.out.println("Match Drawn");
                        break;
                    }
                }
                int move;
                System.out.println("Player "+curr_player+"'s turn");
                if(curr_player==1){
                    move = p1.getTurn(pg, curr_player);
                }
                else if(curr_player==2){
                    move=p2.getTurn(pg, curr_player);
                }
                else continue;

                if(move==-1){
                    continue;
                }
                System.out.println("Given Move : "+ (move+1));
                boolean addi_move= pg.giveTurn(curr_player, move);
                pg.showGround();
                if(addi_move){
                    System.out.println("Additional Move for player "+ curr_player);
                    continue;
                }
                if(curr_player==1) curr_player=2;
                else if(curr_player==2) curr_player=1;
            }
        }
    }
}
