package search.strips.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import search.algorithms.BreadthFirst;
import search.algorithms.DepthFirst;
import search.algorithms.IterativeDeepening;
import search.interfaces.Node;
import search.interfaces.Solver;
import search.strips.model.Fluent;
import search.strips.model.FluentTypes;
import search.strips.model.State;
import search.strips.model.StripsNode;

public class Planning {

    public static void main(String[] args) {
        Set<String> blocks = Set.of("A","B","C");

        State initialState = new State(
            Set.of(
                new Fluent(FluentTypes.ON, List.of("A","t")),
                new Fluent(FluentTypes.ON, List.of("B","t")),
                new Fluent(FluentTypes.ON, List.of("C","t")),
                new Fluent(FluentTypes.CLEAR, List.of("A")),
                new Fluent(FluentTypes.CLEAR, List.of("B")),
                new Fluent(FluentTypes.CLEAR, List.of("C"))
            )
        );

        State finalState = new State(
            Set.of(
                new Fluent(FluentTypes.ON, List.of("A","B")),
                new Fluent(FluentTypes.ON, List.of("B","C")),
                new Fluent(FluentTypes.ON, List.of("C","t")),
                new Fluent(FluentTypes.CLEAR, List.of("A"))
            )
        );

        StripsNode.setStates(initialState, finalState, blocks);


        StripsNode root = new StripsNode(null,new LinkedList<>(),finalState);

        Solver alg = new IterativeDeepening();
        Node result = alg.solve(root);
        System.out.println(result);
    }
    
}
