package search.implementation.strips.model;

import java.util.List;

public class Fluent {
    private FluentTypes type;
    private List<String> values;


    public Fluent(FluentTypes type, List<String> values) {
        this.type = type;
        if(type==FluentTypes.CLEAR && values.size()!=1 
            || type==FluentTypes.ON && values.size()!=2) throw new IllegalArgumentException("Wrong fluent type or argument");
        this.values = values;
    }
    public FluentTypes getType() {
        return type;
    }
    public List<String> getValues() {
        return values;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((values == null) ? 0 : values.hashCode());
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
        Fluent other = (Fluent) obj;
        if (type != other.type)
            return false;
        if (values == null) {
            if (other.values != null)
                return false;
        } else if (!values.equals(other.values))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Fluent [type=" + type + ", values=" + values + "]";
    }

    

    
 
}

