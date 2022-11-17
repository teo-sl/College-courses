package adversary.implementations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import adversary.enumerations.Color;
import adversary.interfaces.Board;
import adversary.interfaces.Move;

public class BoardTicTacToe implements Board {
    private Color[][] board = new Color[3][3];
    private Color lastMovePlayer=null;

    public BoardTicTacToe() {
        for(int i=0;i<3;++i)
            for(int j=0;j<3;j++)
                board[i][j] = Color.NONE;
    }


    private boolean isTerminalP(Color c) {
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board[i][j]!=c) break;
                else if(j==2) return true;
        for(int j=0;j<3;++j)
            for(int i=0;i<3;++i)
                if(board[i][j]!=c) break;
                else if(i==2) return true;
        for(int i=0;i<3;++i)
            if(board[i][i]!=c) break;
            else if(i==2) return true;
        int j=2;
        for(int i=0;i<3;++i,--j)
            if(board[i][j]!=c) break;
            else if(i==2) return true;
        return false;
    }
    private boolean isFull() {
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board[i][j]==Color.NONE) return false;
        return true;
    }
    public boolean isTerminal() {
        return isFull() || isTerminalP(Color.BLACK) || isTerminalP(Color.WHITE);
    }

    @Override
    public List<Move> getPossibleMoves() {
        Color cur;
        if(lastMovePlayer==null || lastMovePlayer==Color.WHITE)
            cur = Color.BLACK;
        else
            cur = Color.WHITE;

        LinkedList<Move> moves = new LinkedList<>();
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board[i][j]==Color.NONE)
                    moves.add(new TicTacMove(i, j, cur));
        return moves;
    }

    @Override
    public void apply(Move m) {
        if(!(m instanceof TicTacMove)) throw new IllegalArgumentException();
        TicTacMove tm = (TicTacMove) m;
        int i=tm.getI(),j=tm.getJ();
        if(board[i][j]!=Color.NONE) throw new IllegalArgumentException("Move non available");
        lastMovePlayer=tm.getPlayer();
        board[i][j]=lastMovePlayer;
    }

    @Override
    public void revert(Move m) {
        if(!(m instanceof TicTacMove)) throw new IllegalArgumentException();
        TicTacMove tm = (TicTacMove) m;
        int i=tm.getI(),j=tm.getJ();
        if(board[i][j]==Color.NONE) throw new IllegalArgumentException("Revert impossible");
        Color prevCur;
        if(tm.getPlayer()==Color.BLACK)
            prevCur=Color.WHITE;
        else
            prevCur=Color.BLACK;
        lastMovePlayer=prevCur;
        board[i][j]=Color.NONE;
    }

    @Override
    public double evaluateBoard(Color self) {
        if(isTerminal()) {
            if(isFull()) return 0;
            boolean winB = isTerminalP(Color.BLACK);
            if(winB && self==Color.BLACK) return 1;
            else return -1;
        }
        double counter_B=0;
        double counter_W=0;
        double counter_N=0;
        double B,W;
        double[] decodeCounters;
        B=W=0;

        
        for(int i=0;i<3;++i) {
            counter_B=counter_W=0;
            for(int j=0;j<3;++j)
                if(board[i][j]==Color.BLACK) counter_B++;
                else if(board[i][j]==Color.WHITE) counter_W++;
                else counter_N++;
            decodeCounters = decodeCounters(counter_B,counter_W);
            B+=decodeCounters[0];
            W+=decodeCounters[1];
        }
        
        

        for(int j=0;j<3;++j) {
            counter_B=counter_W=0;
            for(int i=0;i<3;++i)
                if(board[i][j]==Color.BLACK) counter_B++;
                else if(board[i][j]==Color.WHITE) counter_W++;
            decodeCounters = decodeCounters(counter_B,counter_W);
            B+=decodeCounters[0];
            W+=decodeCounters[1];
        }
        
        

        counter_B=counter_W=0;
        for(int i=0;i<3;++i)
            if(board[i][i]==Color.BLACK) counter_B++;
            else if(board[i][i]==Color.WHITE) counter_W++;
        decodeCounters = decodeCounters(counter_B,counter_W);

        B+=decodeCounters[0];
        W+=decodeCounters[1];

        counter_B=counter_W=0;
        int j=2;
        for(int i=0;i<3;++i,--j)
            if(board[i][j]==Color.BLACK) counter_B++;
            else if(board[i][j]==Color.WHITE) counter_W++;

        decodeCounters = decodeCounters(counter_B,counter_W);

        B+=decodeCounters[0];
        W+=decodeCounters[1];

        double ret = (B-W)/(B+W+counter_N);
        if(self==Color.BLACK)
            return ret;
        else 
            return -1*ret;
    }


    private double[] decodeCounters(double counter_B, double counter_W) {
        double[] ret = new double[2];
        ret[0]=ret[1]=0;

        if(counter_B!=0 && counter_W!=0) {
            ret[0]=counter_B;
            ret[1]=counter_W;
            return ret;
        }

        if(counter_B!=0) {
            if(counter_B==2)
                if(lastMovePlayer==Color.WHITE) ret[0]+=100;
                else ret[0]+=10;
            else
                ret[0]+=1;
        }
        if(counter_W!=0) {
            if(counter_W==2)
                if(lastMovePlayer==Color.BLACK) ret[1]+=100;
                else ret[1]+=10;
            else
                ret[1]+=1;
        }

        return ret;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Color[] r : board)
            sb.append(Arrays.toString(r)+"\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        BoardTicTacToe board = new BoardTicTacToe();
        Scanner sc = new Scanner(System.in);
        Color cur = Color.BLACK;
        Color nextCur = Color.WHITE;
        String player="";
        Color self = Color.BLACK;
        //AlphaBeta artificialPlayer = new AlphaBeta();
        Move move;
        while(!board.isTerminal()) {
            switch(cur) {
                case BLACK:
                    player = " x ";
                    nextCur=Color.WHITE;
                    break;
                case WHITE:
                    player = " o ";
                    nextCur=Color.BLACK;
                    break;
                case NONE:
                    throw new IllegalArgumentException();
            }
            /* 
            if(cur==Color.WHITE) {
                move = artificialPlayer.getBestMove(board, Color.WHITE);
            }
            else {
            */
                System.out.println("PLayer"+player+" make your move");
                int i = sc.nextInt();
                int j = sc.nextInt();
                move = new TicTacMove(i, j, cur);
            
            //}
            board.apply(move);
            System.out.println(board);
            System.out.println(board.evaluateBoard(self));
            cur=nextCur;

            
        }

        sc.close();
    }

    
}
