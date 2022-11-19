package projectTicTacToe.algorithm;

import java.util.List;

import projectTicTacToe.interfaces.Board;
import projectTicTacToe.interfaces.MinimaxResult;
import projectTicTacToe.interfaces.Move;
import projectTicTacToe.interfaces.Player;

public class MinimaxAB {
    
    public static final int LOSS = -1000;
    public static final int WIN = +1000;
    public static final int DRAW = 0;
    
    public Move getBestMove(Board board,Player player) {
        return minimaxOptimization(board, player, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).getBestMove();
    }
    
    private MinimaxResult minimaxOptimization(Board board, Player player, int depth, int alpha, int beta) {

        Move bestMove = null;
        int bestScore = (player==Player.AI) ? LOSS : WIN;

        if(board.isTerminal()) {
            bestScore = board.evaluateState(Player.AI);
            return new MinimaxResult(bestScore, bestMove);
        }
        List<Move> moves = board.getPossibleMoves(player);

        for(Move m : moves ) {
            board.apply(m);
            boolean reversed = false;

            if(player==Player.AI) {
                int score = minimaxOptimization(board, Player.HUMAN, depth+1, alpha, beta).getBestScore();

                if(bestScore<score) {
                    bestScore = score-depth*10;
                    bestMove = m;

                    alpha = Math.max(alpha, bestScore);
                    board.reverse(m); reversed=true;
                    if(beta<=alpha)
                        break;
                }
            }
            else {
                int score = minimaxOptimization(board, Player.AI, depth+1, alpha, beta).getBestScore();
                if(bestScore>score) {
                    bestScore=score+depth*10;
                    bestMove = m;
                    beta = Math.min(beta, bestScore);
                    board.reverse(m); reversed=true;
                    if(beta<=alpha)
                        break;

                }
            }
            if(!reversed) board.reverse(m);
        }
        return new MinimaxResult(bestScore, bestMove);
    }
}

