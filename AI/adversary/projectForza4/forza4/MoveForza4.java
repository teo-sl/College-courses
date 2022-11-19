package projectForza4.forza4;

import projectForza4.enumerations.Player;
import projectForza4.interfaces.Move;

public class MoveForza4 implements Move {
    private int column;
    private Player player;

    

    public MoveForza4(int column, Player player) {
        this.column = column;
        this.player = player;
    }

    



    @Override
    public Player getPlayer() {
        return player;
    }


    public int getColumn() {
        return column;
    }





    @Override
    public String toString() {
        return "MoveForza4 [column=" + column + ", player=" + player + "]";
    }

    
}
