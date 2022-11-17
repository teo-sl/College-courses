package adversary.algotithms;

import adversary.enumerations.Color;
import adversary.interfaces.Board;
import adversary.interfaces.Move;

public class AlphaBeta {

    private static double INF = Integer.MAX_VALUE;
    
    private Color selfColor;
    private Move bestMove;


    public Move getBestMove(Board board, Color selfColor) {
        this.selfColor=selfColor;

        minimax(board, 42, selfColor, true, -INF, INF);


        return bestMove;
    }


    private double minimax(Board board, int depth, Color current, boolean maximizing, double alpha, double beta) {


        if (depth == 0 || board.isTerminal()) {         //nodo terminale o 
            return ((current == selfColor) ? 1 : -1) *
                board.evaluateBoard(current,selfColor);
        } else if (maximizing) {
            double best = -INF;
            for (Move m: board.getPossibleMoves(current)) {

                board.apply(m);

                double childVal = minimax(board, depth - 1, current, false, alpha, beta);
                
                board.revert(m);

                if (childVal > best) {
                    best = childVal;
                    alpha = Math.max(alpha, best);
                    this.bestMove = m;

                    if (alpha >= beta) {
                        break;
                    }
                }
            }

            return best;
        }

        double best = INF;

        for (Move m: board.getPossibleMoves(current)) {

            board.apply(m);
            best = Math.min(best, minimax(board, depth - 1, current,
                true, alpha, beta));
            board.revert(m);
            beta = Math.min(beta, best);

            if (alpha >= beta) {
                break;
            }
        }

        return best;
    }
}