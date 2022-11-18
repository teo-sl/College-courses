package project.tests;

import java.util.Scanner;

import project.algorithm.MinimaxAB;
import project.implementations.TicTacToeMove;
import project.implementations.tictactoe.BoardTicTacToe;
import project.interfaces.Player;

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
