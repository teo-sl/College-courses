package search.implementation.strips.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import search.algorithms.BestFirst;
import search.algorithms.IterativeDeepening;
import search.implementation.strips.model.Fluent;
import search.implementation.strips.model.FluentTypes;
import search.implementation.strips.model.State;
import search.implementation.strips.model.StripsNode;
import search.interfaces.Node;
import search.interfaces.Solver;

public class Planning {

    public static void main(String[] args) {
        Set<String> blocks = Set.of("A","B","C","D","E","F");

        State initialState = new State(
            Set.of(
                new Fluent(FluentTypes.ON, List.of("A","B")),
                new Fluent(FluentTypes.ON, List.of("B","C")),
                new Fluent(FluentTypes.ON, List.of("C","t")),
                new Fluent(FluentTypes.CLEAR, List.of("A")),

                new Fluent(FluentTypes.ON, List.of("D","E")),
                new Fluent(FluentTypes.ON, List.of("E","F")),
                new Fluent(FluentTypes.ON, List.of("F","t")),
                new Fluent(FluentTypes.CLEAR, List.of("D"))
                //new Fluent(FluentTypes.CLEAR, List.of("B")),
                //new Fluent(FluentTypes.CLEAR, List.of("C"))
            )
        );

        State finalState = new State(
            Set.of(
                new Fluent(FluentTypes.ON, List.of("A","D")),
                new Fluent(FluentTypes.ON, List.of("D","B")),
                new Fluent(FluentTypes.ON, List.of("B","t")),
                new Fluent(FluentTypes.CLEAR, List.of("A")),

                new Fluent(FluentTypes.ON, List.of("C","E")),
                new Fluent(FluentTypes.ON, List.of("E","F")),
                new Fluent(FluentTypes.ON, List.of("F","t")),
                new Fluent(FluentTypes.CLEAR, List.of("C"))
            )
        );

        StripsNode.setStates(initialState, finalState, blocks);


        
        StripsNode root = new StripsNode(null,new LinkedList<>(),finalState);

        Solver alg = new BestFirst();

        long start =System.currentTimeMillis();
        Node result = alg.solve(root);
        long end =System.currentTimeMillis();

        System.out.println(result);

        System.out.println("\n\nTime of execution: "+(end-start));
    }
    
}
