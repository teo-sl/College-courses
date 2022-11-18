package project.implementations;

import project.interfaces.Move;
import project.interfaces.Player;

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
