package projectForza4.forza4;

import java.util.LinkedList;
import java.util.List;

import projectForza4.enumerations.Player;
import projectForza4.interfaces.Board;
import projectForza4.interfaces.GameResult;
import projectForza4.interfaces.Move;

public class BoardForza4 implements Board {
    private Player[][] board = new Player[6][7];
    //private static int WINDOW_LENGTH = 4;


    public BoardForza4() {
        for(int i=0;i<board.length;++i)
            for(int j=0;j<board[0].length;++j)
                board[i][j]=null;
    }
    

    @Override
    public boolean isTerminal() {
        return isFull() || isWinningPlayer(Player.AI) || isWinningPlayer(Player.HUMAN);
    }

    private boolean isWinningPlayer(Player player) {

        // orizontal locations
        for(int j=0;j<board[0].length-3;++j)
            for(int i=0;i<board.length;++i)
                if(board[i][j]==player && 
                    board[i][j+1]==player && 
                    board[i][j+2]==player && 
                    board[i][j+3]==player) return true;

        // vertical locations        
        for(int j=0;j<board[0].length;++j)
            for(int i=0;i<board.length-3;++i)
                if(board[i][j]==player && 
                    board[i+1][j]==player && 
                    board[i+2][j]==player && 
                    board[i+3][j]==player) return true;
        
        // positive diagonal
        for(int j=0;j<board[0].length-3;++j)
            for(int i=0;i<board.length-3;++i)
                if(board[i][j]==player && 
                board[i+1][j+1]==player && 
                board[i+2][j+2]==player && 
                board[i+3][j+3]==player) return true;

        // negative diagonal
        for(int j=0;j<board[0].length-3;++j)
            for(int i=3;i<board.length;++i)
                if(board[i][j]==player && 
                board[i-1][j+1]==player && 
                board[i-2][j+2]==player && 
                board[i-3][j+3]==player) return true;

        return false;
    }


    private boolean isFull() {
        for(int i=0;i<board.length;++i)
            for(int j=0;j<board[0].length;++j)
                if(board[i][j]==null) return false;
        return true;
    }





    @Override
    public void apply(Move m) {
        if(!(m instanceof MoveForza4)) throw new IllegalArgumentException();
        MoveForza4 fm = (MoveForza4) m;

        int c = fm.getColumn();
        for(int i=board.length-1;i>=0;--i)
            if(board[i][c]==null) {
                board[i][c]=m.getPlayer();
                break;
            }
                
    }

    @Override
    public void revert(Move m) {
        if(!(m instanceof MoveForza4)) throw new IllegalArgumentException();
        MoveForza4 fm = (MoveForza4) m;
        Player player = fm.getPlayer();
        int c = fm.getColumn();
        for(int i=0;i<board.length;i++)
            if(board[i][c]!=null && board[i][c]==player) {
                board[i][c]=null;
                break;
            }
    }

    public Player getWinner() {
        if(!isTerminal()) return null;
        if(isFull()) return null;
        if(isWinningPlayer(Player.AI)) return Player.AI;
        return Player.HUMAN;
    }


    @Override
    public List<Move> getValidMoves(Player player) {
        LinkedList<Move> moves = new LinkedList<>();

        int rowToCheck =0;
        for(int j=0;j<board[0].length;++j)
            if(board[rowToCheck][j]==null)
                moves.add(new MoveForza4(j, player));
        return moves;
    }




    @Override
    public int score(Player player) {
        if(isFull()) return GameResult.DRAW;
        if(isWinningPlayer(player)) return GameResult.WIN;
        if(isWinningPlayer(getOpponent(player))) return GameResult.LOSS;

        int score = 0;

        List<Player> window = new LinkedList<>();

        // orizontal locations
        for(int j=0;j<board[0].length-3;++j) {
            for(int i=0;i<board.length;++i) {
                window.add(board[i][j]);window.add(board[i][j+1]);
                window.add(board[i][j+2]);window.add(board[i][j+3]);
                score+=evaluateWindow(window,player);
                window.clear();
            }
            
        }
        
        // vertical locations        
        for(int j=0;j<board[0].length;++j) {
            for(int i=0;i<board.length-3;++i) {
                window.add(board[i][j]);window.add(board[i+1][j]);
                window.add(board[i+2][j]);window.add(board[i+3][j]);
                score+=evaluateWindow(window,player);
                window.clear();
            }
            
        }
                

        for(int j=0;j<board[0].length-3;++j) {
            for(int i=0;i<board.length-3;++i) {
                window.add(board[i][j]);window.add(board[i+1][j+1]);
                window.add(board[i+2][j+2]);window.add(board[i+3][j+3]);
                score+=evaluateWindow(window,player);
                window.clear();
            }
            
        }
                

        // negative diagonal
        for(int j=0;j<board[0].length-3;++j) {
            for(int i=3;i<board.length;++i) {
                window.add(board[i][j]); window.add(board[i-1][j+1]); 
                window.add(board[i-2][j+2]); window.add(board[i-3][j+3]);
                score+=evaluateWindow(window,player);
                window.clear();
            }
            
        }
         
        return score;
        
    }





    private int evaluateWindow(List<Player> window, Player player) {
        int score = 0;
        int playerCount = 0, opponentCount = 0, emptyCount = 0;
        for(Player p : window) {
            if(p==null)
                emptyCount++;
            else if(p==player)
                playerCount++;
            else
                opponentCount++;
        }
        if(playerCount==4) score+=100;
        else if(playerCount==3 && emptyCount==1) score+=5;
        else if(playerCount==2 && emptyCount==2) score+=2;

        if(opponentCount==3 && emptyCount==1) score-=4;

        return score;
        
    }


    private Player getOpponent(Player player) {
        if(player==Player.AI) return Player.HUMAN;
        return Player.AI;
    }

    public void setBoard(Player[][] board) {
        this.board=board;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board:\n");
        for(int i=0;i<board.length;++i) {
            sb.append("|");
            for(int j=0;j<board[0].length;++j) {
                String el = " ";
                if(board[i][j]==null) el=" ";
                else if(board[i][j]==Player.HUMAN) el="X";
                else el="O";

                String line=String.format("%6s",el);
                sb.append(line);

                if(j==board[0].length-1)
                    sb.append("|\n");
                else sb.append("|");
            }
        }
        sb.append("--------------------------------------------------\n");
        for(int i=0;i<=board.length;++i) {
            sb.append("|");
            String el=(i)+"";
            sb.append(String.format("%6s", el));
            if(i==board.length)
                sb.append("|");
        }
        sb.append("\n");
        return sb.toString();
    }




    
}








class Pair<T,U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T get0() {
        return first;
    }

    public U get1() {
        return second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair<T,U> other = (Pair<T,U>) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }
}
