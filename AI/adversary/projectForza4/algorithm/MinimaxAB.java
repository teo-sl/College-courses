package projectForza4.algorithm;

import java.util.Collections;
import java.util.List;

import projectForza4.enumerations.Player;
import projectForza4.interfaces.Board;
import projectForza4.interfaces.MinimaxResult;
import projectForza4.interfaces.Move;

public class MinimaxAB {
    

    
    public Move getBestMove(Board board,Player player, int depth) {
        return minimaxOptimization(board, player, depth, Integer.MIN_VALUE, Integer.MAX_VALUE).getBestMove();
    }
    
    private MinimaxResult minimaxOptimization(Board board, Player player, int depth, int alpha, int beta) {

        Move bestMove = null;
        int bestScore = (player==Player.AI) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if(depth == 0 || board.isTerminal()) {
            bestScore = board.score(Player.AI);
            return new MinimaxResult(bestScore, bestMove);
        }

        List<Move> moves = board.getValidMoves(player); 
        Collections.shuffle(moves);

        for(Move m : moves ) {
            board.apply(m);
            boolean reversed = false;

            if(player==Player.AI) {
                int score = minimaxOptimization(board, Player.HUMAN, depth-1, alpha, beta).getBestScore();

                if(bestScore<score) {
                    bestScore = score;
                    bestMove = m;

                    alpha = Math.max(alpha, bestScore);
                    board.revert(m); reversed=true;
                    if(beta<=alpha)
                        break;
                }
            }
            else {
                int score = minimaxOptimization(board, Player.AI, depth-1, alpha, beta).getBestScore();
                if(bestScore>score) {
                    bestScore=score;
                    bestMove = m;
                    beta = Math.min(beta, bestScore);
                    board.revert(m); reversed=true;
                    if(beta<=alpha)
                        break;

                }
            }
            if(!reversed) board.revert(m);
        }
        return new MinimaxResult(bestScore, bestMove);
    }
}