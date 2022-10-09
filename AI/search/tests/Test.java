package search.tests;

import java.util.List;

import search.algorithms.BestFirst;
import search.algorithms.BreadthFirst;
import search.algorithms.DepthFirst;
import search.algorithms.IterativeDeepening;
import search.implementation.Puzzle8;
import search.interfaces.Node;

public class Test {
    public static void main(String[] args) {
        int[][] data = {
            {5,4,-1},
            {6,1,8},
            {7,3,2}
        };  

        Node root = new Puzzle8(data);
        Node solution = new BestFirst().solve(root);
        System.out.println(solution);

        System.out.println("\n\n");
        List<Node> path = solution.getPath();
        for(Node n : path)
            System.out.println(n+"\n ---------------- \n");
        

        
    }
    
}
