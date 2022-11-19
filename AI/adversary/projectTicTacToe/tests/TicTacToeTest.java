package projectTicTacToe.tests;

import java.util.Scanner;

import projectTicTacToe.algorithm.MinimaxAB;
import projectTicTacToe.implementations.TicTacToeMove;
import projectTicTacToe.implementations.tictactoe.BoardTicTacToe;
import projectTicTacToe.interfaces.Player;

public class TicTacToeTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BoardTicTacToe board = new BoardTicTacToe();
        MinimaxAB ai = new MinimaxAB();

        Player current = Player.AI;

        System.out.println(board);
        while(!board.isTerminal()) {
            if(current==Player.HUMAN) {
                System.out.println("Do your move x:");
                int i = sc.nextInt();
                int j = sc.nextInt();
                board.apply(new TicTacToeMove(i, j, Player.HUMAN));

                current=Player.AI;
            }
            else {
                TicTacToeMove aiMove = (TicTacToeMove) ai.getBestMove(board, Player.AI);
                System.out.println("Ai move: i: "+aiMove.getI()+ " j: "+aiMove.getJ());
                board.apply(aiMove);
                current = Player.HUMAN;
            }

            System.out.println(board);
            
        }
        Player winner = board.getWinner();
        if(winner==null) System.out.println("Draw");
        else System.out.println(winner+", you are the winner");

        sc.close();
    }
    
}
