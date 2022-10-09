package search.tests;

import java.util.List;

import search.algorithms.BestFirst;
import search.algorithms.BreadthFirst;
import search.algorithms.DepthFirst;
import search.algorithms.IterativeDeepening;
import search.implementation.Cannibals;
import search.interfaces.Node;

public class TestMC {
    public static void main(String[] args) {
        Node root = new Cannibals();
        Node solution = new IterativeDeepening().solve(root);
        System.out.println(solution);

        System.out.println("\n\n");
        List<Node> path = solution.getPath();
        for(Node n : path)
            System.out.println(n+"\n ---------------- \n");
        
        
    }
    
}
