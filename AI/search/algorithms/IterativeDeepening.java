package search.algorithms;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import search.interfaces.Node;
import search.interfaces.Solver;

public class IterativeDeepening implements Solver {

    @Override
    public Node solve(Node root) {
        Result r;
        for(int l=1;;++l){
            r = depthFirst(root,l);
            if(r.getNode()!=null) return r.getNode();
            if(!r.gestStatus())
                break;
        }
        return null;
    }

    private Result depthFirst(Node root, int d) {
        List<Node> L = new ArrayList<>();
        L.add(root);
        boolean status = true;
        while(!L.isEmpty()) {

            Node n = L.get(0);
            L.remove(n);
            
            if(n.isGoal())
                return new Result(n, status);
            else
                if(n.getDepth()<d) {
                    List<Node> sons = n.getSons();
                    if(sons.size()==0 && L.size()==0)
                        status=false;
                    for(Node s : sons)
                            L.add(0,s);
                }
        }

        return new Result(null, status);
    }

    private class Result {
        private Node node;
        private boolean status;

        public Result(Node node, boolean status) {
            this.node=node;
            this.status=status;
        }

        public Node getNode() {
            return node;
        }

        public boolean gestStatus() {
            return status;
        }
    }
    
}
