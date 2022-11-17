package adversary.implementations;

import java.util.Arrays;

import adversary.enumerations.Color;
import adversary.interfaces.Move;

public class TicTacMove implements Move {
    private int[] coordinates = new int[2];
    private Color player;
    public TicTacMove(int i, int j, Color player) {
        this.coordinates[0] = i;
        this.coordinates[1] = j;
        this.player = player;
    }
    public int getI() {
        return coordinates[0];
    }
    public int getJ() {
        return coordinates[1];
    }

    public Color getPlayer() {
        return player;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(coordinates);
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TicTacMove other = (TicTacMove) obj;
        if (!Arrays.equals(coordinates, other.coordinates))
            return false;
        if (player != other.player)
            return false;
        return true;
    }

    
    
}
