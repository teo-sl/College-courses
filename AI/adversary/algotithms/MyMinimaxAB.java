package adversary.algotithms;

import adversary.enumerations.Color;
import adversary.interfaces.Board;
import adversary.interfaces.Move;

public class MyMinimaxAB {
    private Color player;
    private Move bestMove = null;

    public Move getBestMove(Board board, int depth, boolean maximizer, Color player) {
        this.player=player;
        double v = minimax(board, depth, Double.MIN_VALUE, Double.MAX_VALUE, maximizer);
        System.out.println("mini"+v);
        return bestMove;
    }

    private double minimax(Board board, int depth,double alpha, double beta, boolean maximizer) {
        if(depth==0 || board.isTerminal()) 
            return board.evaluateBoard(player);
        double eval,minEval,maxEval;
        if(maximizer) {
            maxEval = -Integer.MIN_VALUE;
            for(Move m : board.getPossibleMoves()) {
                board.apply(m);
                eval = minimax(board, depth-1, alpha, beta,false);
                board.revert(m);

                if(eval>maxEval) {
                    bestMove=m;
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if(beta<=alpha)
                    break;
            }
            return maxEval;
        }
        else {
            minEval = Integer.MAX_VALUE;
            for(Move m : board.getPossibleMoves()) {
                board.apply(m);
                eval = minimax(board, depth, alpha, beta, maximizer);
                board.revert(m);
                minEval = Math.min(eval, minEval);
                beta = Math.min(beta, eval);
                if(beta<=alpha)
                    break;
            }
            return minEval;
        }
    }
    
}
