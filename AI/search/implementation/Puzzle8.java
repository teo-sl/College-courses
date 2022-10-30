package search.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import search.abstraction.AbstractNode;
import search.interfaces.Node;
import search.util.Util;

public class Puzzle8 extends AbstractNode {

    private int[] whitePosition = new int[2];

    private int[][] data;

    private static Map<Integer, Integer[]> ref = Map.of(
       1 , new Integer[]{0,0},
       2 , new Integer[]{0,1},
       3 , new Integer[]{0,2},
       4 , new Integer[]{1,0},
       5 , new Integer[]{1,2},
       6 , new Integer[]{2,0},
       7 , new Integer[]{2,1},
       8 , new Integer[]{2,2}

    );

    public Puzzle8(int[][] data) {
        super(null);
        boolean found=false;
        this.data=data;
        
        for(int i=0;i<3 && !found;++i)
            for(int j=0;j<3 && !found;++j)
                if(data[i][j]==-1) {
                    whitePosition[0]=i;
                    whitePosition[1]=j;
                    found = false;
                    break;
                }
    }

    public Puzzle8(int[][] data, Node father) {
        this(data);
        this.setFather(father);

        this.computeEur();
    }
    

    public void computeEur() {
        cost=0;
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j) {
                if(data[i][j]==-1)
                    continue;
                int x = data[i][j];
                int r = ref.get(x)[0];
                int c = ref.get(x)[1];
                cost+=Math.abs(i-r)+Math.abs(j-c);
            }
        cost+=this.depth;
    }


    @Override
    public boolean isGoal() {
        int k=1;
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j) {
                if(i==1 && j==1 && data[i][j]==-1) continue;
                if(k!=data[i][j]) return false;
                k++;
            }
        return true;
    }

    @Override
    public List<Node> getSons() {
        int i = whitePosition[0];
        int j = whitePosition[1];

        int m = 3, n = 3;

        List<Node> ret = new LinkedList<>();
        int[][] tmp;


        if(j>0) {
            tmp = Util.deepCopy(this.data);
            this.swap(tmp,i,j,i,j-1);
            ret.add(new Puzzle8(tmp,this));
        }
        if(j<n-1) {
            tmp = Util.deepCopy(this.data);
            this.swap(tmp,i,j,i,j+1);
            ret.add(new Puzzle8(tmp,this));
        }
        if(i>0) {
            tmp = Util.deepCopy(this.data);
            this.swap(tmp,i,j,i-1,j);
            ret.add(new Puzzle8(tmp,this));
        }
        if(i<m-1) {
            tmp = Util.deepCopy(this.data);
            this.swap(tmp,i,j,i+1,j);
            ret.add(new Puzzle8(tmp,this));
        }

        return ret;
    }

    private void swap(int[][] m,int i, int j,int r,int c) {
        int tmp = m[i][j];
        m[i][j]=m[r][c];
        m[r][c]=tmp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                sb.append(data[i][j] + " ");
            }
            sb.append('\n');
        }

        sb.append("Cost : "+this.cost+"\n");
        sb.append("Depth : "+this.depth+"\n");

        return sb.toString();
    }
    
}
