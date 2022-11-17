package adversary.algotithms;

import adversary.interfaces.Board;
import adversary.interfaces.Move;

public class MyMinimaxAB {

    double minimax(Board board, int depth,double alpha, double beta, boolean maximizer) {
        if(depth==0 || board.isTerminal()) 
            return board.evaluateBoard();
        double eval,minEval,maxEval;
        if(maximizer) {
            maxEval = -Integer.MIN_VALUE;
            for(Move m : board.getPossibleMoves()) {
                board.apply(m);
                eval = minimax(board, depth-1, alpha, beta,false);
                board.revert(m);
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
