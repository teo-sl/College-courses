package projectTicTacToe.implementations;

import projectTicTacToe.interfaces.Move;
import projectTicTacToe.interfaces.Player;

public class TicTacToeMove implements Move {
    private int i,j;
    private Player player;
    public TicTacToeMove(int i, int j, Player player) {
        this.i = i;
        this.j = j;
        this.player = player;
    }
    public int getI() {
        return i;
    }
    public int getJ() {
        return j;
    }
    public Player getPlayer() {
        return player;
    }

    
    
}
