package adversary.interfaces;

import java.util.List;

import adversary.enumerations.Color;

public interface Board {
    boolean isTerminal();
    List<Move> getPossibleMoves(Color current);
    void apply(Move m);
    void revert(Move m);
    double evaluateBoard(Color current, Color self);

}
