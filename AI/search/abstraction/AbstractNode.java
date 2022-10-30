package search.abstraction;

import java.util.LinkedList;
import java.util.List;

import search.interfaces.Node;



public abstract class AbstractNode implements Node {
    protected int depth;
    protected Node father;
    protected int cost;

    public AbstractNode(Node father) {
        if(father!=null)
            setFather(father);
        else
            depth=0;
        
    }

    public int getDepth() {
        return this.depth;
    }

    public void setFather(Node n) {
        this.father=n;
        this.depth=father.getDepth()+1;
        
    }

    public Node getFather() {
        return father;
    }

    public List<Node> getPath() {
        List<Node> ret = new LinkedList<>();
        ret.add(this);
        Node n = this.father;
        while(n!=null) {
            ret.add(0,n);
            n = n.getFather();
        }
        return ret;
    }

    public int getEur() {
        return cost;
    }
    
}
