package search.implementation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import search.abstraction.AbstractNode;
import search.interfaces.Node;

public class Cannibals extends AbstractNode {

    private int[][] data = new int[2][3];// M C B

    public Cannibals(int[][] data, Node father) {
        this.data=data;
        this.setFather(father);
        this.computeEur();
    }

    // For the root node
    public Cannibals() {
        data[0][0]=3;
        data[0][1]=3;
        data[0][2]=1;

        data[1][0]=0;
        data[1][1]=0;
        data[1][2]=0;


    }


    

    @Override
    public boolean isGoal() {
        return data[0][0]==0 && 
            data[0][1]==0 &&
            data[0][2]==0;
    }

    @Override
    public void computeEur() {
        cost = depth;
        double tmp = data[0][0]+data[0][1];
        cost += Math.ceil(tmp/2);
        if(data[0][2]==0)
            cost+=1;
        
    }

    @Override
    public List<Node> getSons() {
        if(data[0][2]==1)
            return getSonsGen(0,1);
        return getSonsGen(1,0);
    }

    private List<Node> getSonsGen(int i,int j) {
        List<Node> ret = new LinkedList<>();
        int m = data[i][0];
        int c = data[i][1];

        int mm = data[j][0];
        int cc = data[j][1];
        


        if(m>0 && (c<=m-1 || m-1==0) && mm+1>=cc) {
            int[] part = {m-1,c,0},dest = {mm+1,cc,1};
            int[][] tmp = new int[2][2];
            tmp[i]=part;
            tmp[j]=dest;
            ret.add(new Cannibals(tmp,this));

        }
        if(m-2>=0 && (c<=m-2 || m-2==0) && mm+2>=cc) {
            int[] part = {m-2,c,0},dest = {mm+2,cc,1};
            int[][] tmp = new int[2][2];
            tmp[i]=part;
            tmp[j]=dest;
            ret.add(new Cannibals(tmp,this));
        }
        if(c>0 && (mm>=cc+1 || mm==0)) {
            int[] part = {m,c-1,0},dest = {mm,cc+1,1};
            int[][] tmp = new int[2][2];
            tmp[i]=part;
            tmp[j]=dest;
            ret.add(new Cannibals(tmp,this));
        }
        if(c-2>=0 && (mm>=cc+2 || mm==0)) {
            int[] part = {m,c-2,0},dest = {mm,cc+2,1};
            int[][] tmp = new int[2][2];
            tmp[i]=part;
            tmp[j]=dest;
            ret.add(new Cannibals(tmp,this));
        }
        if(m>0 && c>0 && mm+1>=cc+1) {
            int[] part = {m-1,c-1,0},dest = {mm+1,cc+1,1};
            int[][] tmp = new int[2][2];
            tmp[i]=part;
            tmp[j]=dest;
            ret.add(new Cannibals(tmp,this));
        }

        return ret;
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

    public boolean equals(Object o) {
        if(o==null) return false;
        if(o==this) return true;
        if(! (o instanceof Cannibals)) return false;
        Cannibals c = (Cannibals) o;

        for(int i=0;i<2;++i)
            for(int j=0;j<3;++j)
                if(this.data[i][j]!=c.data[i][j]) return false;
        
        return true;
    }

    @Override       
    public int hashCode() {
        int ret = 0;
        for(int[] x : data) 
            ret+=Arrays.hashCode(x);
        return ret;        
    }

}
