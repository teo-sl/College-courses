package projectForza4.forza4.tests;

import java.util.Scanner;

import projectForza4.algorithm.MinimaxAB;
import projectForza4.enumerations.Player;
import projectForza4.forza4.BoardForza4;
import projectForza4.forza4.MoveForza4;

public class Forza4Test {

    public static void main(String[] args) {
        BoardForza4 board = new BoardForza4();
        Scanner sc = new Scanner(System.in);
        System.out.println(board);
        Player cur = Player.HUMAN;
        MinimaxAB ai = new MinimaxAB();

        while(!board.isTerminal()) {
            if(cur==Player.HUMAN) {
                while(true) {
                    try {
                        System.out.println("Human, make your move");
                        int j = sc.nextInt();
                        board.apply(new MoveForza4(j, Player.HUMAN));
                        cur=Player.AI;
                        break;
                    }
                    catch(Exception e) {System.out.println("Move error, retry");}

                }
            }
            else {
                MoveForza4 move = (MoveForza4) ai.getBestMove(board, Player.AI, 10);
                
                System.out.println("Ai move: "+move.getColumn());
                board.apply(move);
                cur=Player.HUMAN;
            }
            System.out.println(board);
        }

        System.out.println("Game ended");
        Player winner = board.getWinner();
        if(winner==null) System.out.println("Draw");
        else if(winner==Player.HUMAN) System.out.println("Winner is Human");
        else System.out.println("Winner is AI");

        sc.close();
    }
    
}
