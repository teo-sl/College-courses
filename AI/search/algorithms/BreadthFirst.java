package search.algorithms;

import java.util.ArrayList;
import java.util.List;

import search.interfaces.Node;
import search.interfaces.Solver;

public class BreadthFirst implements Solver{

    public Node solve(Node root) {
        List<Node> L = new ArrayList<>();
        L.add(root);

        while(!L.isEmpty()) {

            Node n = L.get(0);
            L.remove(n);
            
            if(n.isGoal())
                return n;
            else
                L.addAll(n.getSons());
        }

        return null;
}

    
}
