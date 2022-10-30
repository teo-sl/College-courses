package search.implementation.strips.model;

import java.util.HashSet;
import java.util.Set;

public class State {
    private Set<Fluent> fluents;

    
    public State(Set<Fluent> fluents) {
        this.fluents = new HashSet<>(fluents);
    }
    public Set<Fluent> getFluents() {
        return fluents;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Fluents : \n");
        for(Fluent x : fluents)
            sb.append(x+"\n");
        return sb.toString();
    }
    
    
}
