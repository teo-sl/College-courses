package search.strips.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Action {
    private List<String> arguments;
    private Set<Fluent> preconditions,addList,deleteList;
    private ActionTypes actionType;

    
    
    



    public Action(List<String> arguments,Set<Fluent> preconditions, Set<Fluent> addList, Set<Fluent> deleteList, ActionTypes actionType) {
        this.preconditions = preconditions;
        this.arguments=arguments;
        this.addList = addList;
        this.deleteList = deleteList;
        this.actionType = actionType;
    }







    public static Set<Action> generateActions(State s,Set<String> blocks) {
        Set<Action> ret = new HashSet<>();
        Set<String> blockAndTable = new HashSet<>(blocks);
        blockAndTable.add("t");
        Action a;
        for(String x : blocks) 
            for(String y : blockAndTable) {
                if(x.equals(y)) continue;
                if(!y.equals("t")) {
                    a =
                        new Action(
                            List.of(x,y),
                            Set.of(
                                new Fluent(FluentTypes.CLEAR, List.of(x)),
                                new Fluent(FluentTypes.ON, List.of(x,y))
                            ),
                            Set.of(
                                new Fluent(FluentTypes.ON, List.of(x,"t")),
                                new Fluent(FluentTypes.CLEAR, List.of(y))
                            ),
                            Set.of(
                                new Fluent(FluentTypes.ON, List.of(x,y))
                            ),
                            ActionTypes.TABLE
                        );
                    if(checkCondition(a,s)) ret.add(a);
                }
                
                for(String z : blocks) {
                    if(z.equals(x) || z.equals(y)) continue;

                    a = new Action(List.of(x,y,z),Set.of( 
                                            new Fluent(FluentTypes.CLEAR, List.of(x)),
                                            new Fluent(FluentTypes.ON, List.of(x,y)),
                                            new Fluent(FluentTypes.CLEAR, List.of(z))
                                        ),
                                        Set.of(
                                            new Fluent(FluentTypes.ON, List.of(x,z)),
                                            new Fluent(FluentTypes.CLEAR, List.of(y))
                                        ),
                                        Set.of(
                                            new Fluent(FluentTypes.ON, List.of(x,y)),
                                            new Fluent(FluentTypes.CLEAR, List.of(z))
                                        ), 
                                                
                                        ActionTypes.BLOCKS
                    );
                    if(checkCondition(a,s)) ret.add(a);

                }
            }
            return ret;
    }







    private static boolean checkCondition(Action a, State s) {
        return
            a.getAddList().stream().filter(x->s.getFluents().contains(x)).collect(Collectors.toSet()).size()>0 &&
            a.getDeleteList().stream().filter(x->s.getFluents().contains(x)).collect(Collectors.toSet()).size()==0;
        
    }







    public Set<Fluent> getPreconditions() {
        return preconditions;
    }







    public Set<Fluent> getAddList() {
        return addList;
    }







    public Set<Fluent> getDeleteList() {
        return deleteList;
    }







    public ActionTypes getActionType() {
        return actionType;
    }







    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((preconditions == null) ? 0 : preconditions.hashCode());
        result = prime * result + ((addList == null) ? 0 : addList.hashCode());
        result = prime * result + ((deleteList == null) ? 0 : deleteList.hashCode());
        result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
        return result;
    }







    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Action other = (Action) obj;
        if (preconditions == null) {
            if (other.preconditions != null)
                return false;
        } else if (!preconditions.equals(other.preconditions))
            return false;
        if (addList == null) {
            if (other.addList != null)
                return false;
        } else if (!addList.equals(other.addList))
            return false;
        if (deleteList == null) {
            if (other.deleteList != null)
                return false;
        } else if (!deleteList.equals(other.deleteList))
            return false;
        if (actionType != other.actionType)
            return false;
        return true;
    }







    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("arguments"+this.arguments+"\n");
        
        sb.append("preconditions List:\n");
        for(Fluent x : preconditions)
            sb.append(x+"\n");
        sb.append("add list\n");
        for(Fluent x : addList)
            sb.append(x+"\n");
            sb.append("delete list list\n");
        for(Fluent x : deleteList)
            sb.append(x+"\n");
        
        
        sb.append("\n action type :"+actionType);
    
        return sb.toString();
    

    }







   

    


    
}

