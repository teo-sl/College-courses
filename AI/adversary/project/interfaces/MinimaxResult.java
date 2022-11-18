package project.interfaces;

public class MinimaxResult {

    private int bestScore;
    private Move bestMove;

    public MinimaxResult(int bestScore, Move bestMove) {
        this.bestScore = bestScore;
        this.bestMove = bestMove;
    }

    public int getBestScore() {
        return bestScore;
    }

    public Move getBestMove() {
        return bestMove;
    }  
}
