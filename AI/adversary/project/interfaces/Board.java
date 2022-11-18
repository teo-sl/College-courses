package project.interfaces;

import java.util.List;

public interface Board {

    boolean isTerminal();

    int evaluateState(Player ai);

    void apply(Move m);

    List<Move> getPossibleMoves(Player player);

    void reverse(Move m);

}