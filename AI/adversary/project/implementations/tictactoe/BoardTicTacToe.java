package project.implementations.tictactoe;

import java.util.LinkedList;
import java.util.List;

import project.interfaces.Board;
import project.interfaces.Move;
import project.interfaces.Player;
import project.algorithm.*;
import project.implementations.TicTacToeMove;;

public class BoardTicTacToe implements Board {
    private Player[][] board = new Player[3][3];

    public BoardTicTacToe() {
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                board[i][j]=null;
    }
    private boolean isFull() {
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board!=null) return false;
        return true;
    }

    @Override
    public boolean isTerminal() {
        return (isFull() || (evaluateState(Player.AI)!=MinimaxAB.DRAW));
    }



    @Override
    public int evaluateState(Player player) {
        if(gameIsWon(player))
            return MinimaxAB.WIN;
        if(gameIsWon(getOpponentPlayer(player)))
            return MinimaxAB.LOSS;
        
        if(isFull())
            return MinimaxAB.DRAW;
        return MinimaxAB.DRAW;

    }

    private boolean gameIsWon(Player player) {
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j) {
                if(board[i][j]!=player) break;
                else if(j==2) return true;
            }
        for(int j=0;j<3;++j)
            for(int i=0;i<3;++i) {
                if(board[i][j]!=player) break;
                else if(i==2) return true;
            }
        for(int i=0;i<3;++i) {
            if(board[i][i]!=player) break;
            else if(i==2) return true;
        }
        int j=2;
        for(int i=0;i<3;i++) {
            if(board[i][j]!=player) break;
            else if(i==2) return true;
            j--;
        }
        
        return false;
    }
    private Player getOpponentPlayer(Player player) {
        if(player==Player.AI) return Player.HUMAN;
        return Player.AI;
    }
    @Override
    public void apply(Move m) {
        if(!(m instanceof TicTacToeMove)) throw new IllegalArgumentException();

        TicTacToeMove tm = (TicTacToeMove) m;

        int i = tm.getI(), j = tm.getJ();

        if(board[i][j]!=null) throw new IllegalArgumentException();

        board[i][j] = tm.getPlayer();

        
    }

    @Override
    public List<Move> getPossibleMoves(Player player) {
        LinkedList<Move> moves = new LinkedList<>();

        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board[i][j]==null) 
                    moves.add(new TicTacToeMove(i, j, player));
        return moves;
    }

    @Override
    public void reverse(Move m) {
        if(!(m instanceof TicTacToeMove)) throw new IllegalArgumentException();

        TicTacToeMove tm = (TicTacToeMove) m;

        int i = tm.getI(), j = tm.getJ();

        if(board[i][j]!=tm.getPlayer()) throw new IllegalArgumentException();

        board[i][j] = null;    
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("Board \n");
        String lines = "----";
        for(int i=0;i<3;++i) {
            if(i==1) {
                sb.append(String.format("%6s",lines));
                sb.append(String.format("%6s",lines));
                sb.append(String.format("%6s",lines));
                
                sb.append("\n");
            }
            for(int j=0;j<3;++j) {
                if(j==1) 
                    sb.append("|");
                
                String element;
                if(board[i][j]==null) element=" ";
                else if(board[i][j]==Player.AI) element="O";
                else element="X";
                sb.append(String.format("%6s",element));
                if(j==1 )
                    sb.append("|");
                if(j==2)
                    sb.append("\n");
            }
            if(i==1) {
                sb.append(String.format("%6s",lines));
                sb.append(String.format("%6s",lines));
                sb.append(String.format("%6s",lines));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Player getWinner() {
        if(!isTerminal()) return null;
        return (gameIsWon(Player.AI)) ? Player.AI : Player.HUMAN;
    }
    
}
