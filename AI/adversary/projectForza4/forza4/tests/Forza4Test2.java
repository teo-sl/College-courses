package projectForza4.forza4.tests;

import projectForza4.enumerations.Player;
import projectForza4.forza4.BoardForza4;
import projectForza4.forza4.MoveForza4;

public class Forza4Test2 {
    public static void main(String[] args) {
        BoardForza4 board = new BoardForza4();

        board.apply(new MoveForza4(0, Player.HUMAN));
        System.out.println(board);
        board.apply(new MoveForza4(0, Player.AI));
        System.out.println(board);
        
        System.out.println(board.getValidMoves(Player.HUMAN));
    }
    
}
