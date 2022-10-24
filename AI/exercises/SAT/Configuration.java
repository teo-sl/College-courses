package AI.exercises.SAT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Configuration implements Comparable<Configuration> {
    private List<Integer> configuration;

    public Configuration(List<Integer> configuration) {
        this.configuration=configuration;
    }


    @Override
    public String toString() {
        return "Configuration [configuration=" + configuration + "]";
    }


    @Override
    public int compareTo(Configuration o) {
        if(o.configuration.size()!=this.configuration.size()) throw new IllegalArgumentException();
        for(int i=0;i<configuration.size();++i)
            if(o.configuration.get(i)!=this.configuration.get(i))
                return this.configuration.get(i)-o.configuration.get(i);
        return 0;
    }

    public Configuration getSuccessor() {
        List<Integer> tmp = new ArrayList<>(configuration);
        int i = configuration.size()-1;
        if(configuration.get(i)==0)
            tmp.set(i, 1);
        else {
            tmp.set(i, 0);
            for(int j=i-1;j>=0;--j)
                if(configuration.get(j)==0) {
                    tmp.set(j,1); break;
                }
                else {
                    tmp.set(j,0);
                }
        }
        return new Configuration(tmp);
    }

    public boolean isNotOnes() {
        return configuration.stream().filter(x->x!=1).count()>0;
    }

    public boolean isNotZeros() {
        return configuration.stream().filter(x->x!=0).count()>0;
    }

    public static Configuration getNegative(Clause c, int value) {
        return new Configuration(c.getLiterals().stream().map(x->(x==1 || x==0)?x:value).collect(Collectors.toList()));
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
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
        Configuration other = (Configuration) obj;
        if (configuration == null) {
            if (other.configuration != null)
                return false;
        } else if (!configuration.equals(other.configuration))
            return false;
        return true;
    }
    
    
}
