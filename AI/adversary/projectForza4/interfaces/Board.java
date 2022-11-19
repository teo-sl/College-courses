package projectForza4.interfaces;

import java.util.List;

import projectForza4.enumerations.Player;

public interface Board {
    boolean isTerminal();
    List<Move> getValidMoves(Player player);
    int score(Player player);
    void apply(Move m);
    void revert(Move m);
    
}
