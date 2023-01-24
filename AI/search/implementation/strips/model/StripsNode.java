package search.implementation.strips.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import search.abstraction.AbstractNode;
import search.interfaces.Node;

public class StripsNode extends AbstractNode {
    private static State initialState;
    private static Set<String> blocks;

    private List<Action> actions = new LinkedList<>();
    private State currentState;

    

    public StripsNode(StripsNode father, List<Action> actions, State currentState) {
        super(father);
        this.actions = actions;
        this.currentState = currentState;
    }

    @Override
    public boolean isGoal() {
        return initialState.getFluents().containsAll(currentState.getFluents());
    }

    @Override
    public void computeEur() {
        this.cost=depth;
        int match = (int) currentState.getFluents().stream().map(x->(initialState.getFluents().contains(x))).count();
        int noMatch = 0;
        for(Fluent f : currentState.getFluents()) {
            if(initialState.getFluents().contains(f))
                match++;
            else
                noMatch++;
        }
        this.cost+=StripsNode.initialState.getFluents().size()-match+noMatch;
        
    }

    @Override
    public List<Node> getSons() {
        List<Node> children = new LinkedList<>(); 
        Set<Action> generatedActions = Action.generateActions(this.currentState,blocks);
        for(Action a : generatedActions) {
            LinkedList<Action> newActions = new LinkedList<>(this.actions);
            newActions.addFirst(a);

            State newState = new State(this.currentState.getFluents());
            newState.getFluents().removeAll(a.getAddList());
            newState.getFluents().addAll(a.getPreconditions());

            children.add(new StripsNode(this,newActions,newState));
        }
        return children;
    }

    public static void setStates(State initialState, State finalState, Set<String> blocks) {
        StripsNode.initialState = initialState;
        StripsNode.blocks = blocks;        
    }

    @Override
    public String toString() {
        return "StripsNode [actions=" + actions + " \n, currentState=" + currentState + "\n]";
    }
    

    


    
}
