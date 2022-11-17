package adversary.implementations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import adversary.algotithms.AlphaBeta;
import adversary.enumerations.Color;
import adversary.interfaces.Board;
import adversary.interfaces.Move;

public class BoardTicTac implements Board{
    private int[][] board = new int[3][3];
    private static int x = 1;
    private static int o = -1;

    private int getBlack() {
        return 1;
    }
    private int getWhite() {
        return -1;
    }

    public BoardTicTac() {
        for(int i=0;i<3;++i)
            Arrays.fill(board[i], 0);
    }

    @Override
    public boolean isTerminal() {
        return (checkTerminal(x) || checkTerminal(o));     
    }

    private boolean checkTerminal(int v) {
        boolean flagInt;
        for(int i=0;i<3;++i) {
            flagInt = true;
            for(int j=0;j<3;++j)
                if(board[i][j]!=v) {
                    flagInt=false; break; 
                }
            if(flagInt) {
                return true;
            }
        }
        for(int i=0;i<3;++i) {
            flagInt = true;
            for(int j=0;j<3;++j)
                if(board[j][i]!=v) {
                    flagInt=false; break; 
                }
            if(flagInt) {
                return true;
            }
        }
        flagInt=true;
        for(int i=0;i<3;++i) {
            if(board[i][i]!=v) {
                flagInt=false; break;
            }
        }
        if(flagInt) return true;

        flagInt=true;
        int i=0;
        for(int j=2;j>=0;--j,i++) {
            if(board[i][j]!=v) {
                flagInt=false; break;
            }
        }
        return flagInt;
    }

    @Override
    public List<Move> getPossibleMoves(Color current) {
        LinkedList<Move> moves = new LinkedList<>();
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board[i][j]==0)
                    moves.add(new TicTacMove(i, j,current));
        return moves;
    }

    @Override
    public void apply(Move m) {
        if(!(m instanceof TicTacMove)) throw new IllegalArgumentException("Acquired move is not from tic tac toe game");
        TicTacMove tm = (TicTacMove) m;
        if(board[tm.getI()][tm.getJ()]!=0) throw new IllegalArgumentException("Move fobrbidden, cell "+tm.getI()+", "+tm.getJ()+" is occupied");

        int value;
        switch(tm.getPlayer()) {
            case BLACK :
                value = getBlack();
                break;
            case WHITE :
                value = getWhite();
                break;
            default:
                throw new IllegalArgumentException("JVM is getting crazy");
        }
        
        board[tm.getI()][tm.getJ()]=value; 
    }

    @Override
    public void revert(Move m) {
        if(!(m instanceof TicTacMove)) throw new IllegalArgumentException("Acquired move is not from tic tac toe game");
        TicTacMove tm = (TicTacMove) m;
        board[tm.getI()][tm.getJ()]=0;   
    }

    @Override
    public double evaluateBoard(Color current, Color self) {
        if(isTerminal()) return 1;
        double x_v = 0;
        double o_v = 0;
        double counter_x, counter_o;
        double[] values;

        for(int i=0;i<3;++i) {
            counter_x=counter_o=0;
            for(int j=0;j<3;++j) {
                if(board[i][j]==1) counter_x++;
                else if(board[i][j]==-1) counter_o++;
            }
            values = evaluateCount(current, counter_x, counter_o);
            x_v+=values[0];
            o_v+=values[1];
        }

        for(int i=0;i<3;++i) {
            counter_x=counter_o=0;
            for(int j=0;j<3;++j) {
                if(board[j][i]==1) counter_x++;
                else if(board[j][i]==-1) counter_o++;
            }
            values = evaluateCount(current, counter_x, counter_o);
            x_v+=values[0];
            o_v+=values[1];
        }
        counter_o=counter_x=0;
        for(int i=0;i<3;++i) {
            if(board[i][i]==1) counter_x++;
            else if(board[i][i]==-1) counter_o++;
        }
        values = evaluateCount(current, counter_x, counter_o);
        x_v+=values[0];
        o_v+=values[1];
        
        counter_o=counter_x=0;
        int i = 0;
        for(int j=2;j>=0;--j,i++) {
            if(board[i][j]==1) counter_x++;
            else if(board[i][j]==-1) counter_o++;
        }
        
        values = evaluateCount(current, counter_x, counter_o);
        x_v+=values[0];
        o_v+=values[1];

        switch(self) {
            case BLACK:
                return (x_v-o_v)/((x_v+o_v));
            case WHITE:
                return (o_v-x_v)/((x_v+o_v));
            default:
                throw new IllegalArgumentException("Color not recognized in evaluate board");
        }

    }


    private double[] evaluateCount(Color current, double count_x, double count_o) {
        double[] ret = new double[2];
        ret[0] = 0; ret[1] = 0;
        if(count_o!=0 && count_x!=0) return ret;

        if(count_o!=0) {
            if(count_o==2) {
                if(current==Color.BLACK) ret[1]+=10000;
                else ret[1]+=15;
            }
            else if(count_o==1) ret[1]+=2;
        }
        if(count_x!=0) {
            if(count_x==2) {
                if(current==Color.WHITE) ret[0]+=10000;
                else ret[0]+=15;
            }
            else if(count_x==1) ret[0]+=2;
        }
        return ret;
        
        


    }

    private int empyt_cells() {
        int c=0;
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j)
                if(board[i][j]==0) c++;
        return c;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int[] r : board)
            sb.append(Arrays.toString(r)+"\n");
        return sb.toString();
    }
    public static void main(String[] args) {

        BoardTicTac board = new BoardTicTac();
        Scanner sc = new Scanner(System.in);
        Color cur = Color.BLACK;
        Color nextCur = Color.WHITE;
        String player="";
        Color self = Color.BLACK;

        AlphaBeta artificialPlayer = new AlphaBeta();
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
            }
            if(cur==Color.WHITE) {
                move = artificialPlayer.getBestMove(board, Color.WHITE);
            }
            else {
                System.out.println("PLayer"+player+" make your move");
                int i = sc.nextInt();
                int j = sc.nextInt();
                move = new TicTacMove(i, j, Color.BLACK);

            }
            board.apply(move);
            System.out.println(board);
            cur=nextCur;

            if(board.getPossibleMoves(cur).isEmpty()) {
                System.out.println("Draw");
                break;
            }
            
        }

        sc.close();

    }

    
}
