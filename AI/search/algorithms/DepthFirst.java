package search.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import search.interfaces.Node;
import search.interfaces.Solver;

public class DepthFirst implements Solver {

    @Override
    public Node solve(Node root) {
        List<Node> L = new ArrayList<>();
        L.add(root);
        Set<Node> visited = new HashSet<>();

        while(!L.isEmpty()) {

            Node n = L.get(0);
            L.remove(n);
            visited.add(n);
            
            if(n.isGoal())
                return n;
            else
                for(Node s : n.getSons()) {
                    if(!visited.contains(s))
                        L.add(0,s);
                }
        }

        return null;
}
    
}
