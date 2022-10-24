package AI.exercises.SAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SpringLayout.Constraints;

public class Clause {
    private int nVariables;
    private List<Integer> literals;


    public Clause(int nVariables, List<Integer> literals) {
        //if(nVariables<3) throw new IllegalArgumentException();
        //if(literals.size()!=3) throw new IllegalArgumentException();
        if(literals.stream().map(x->Math.abs(x)).filter(x->x>nVariables).count()>0) throw new IllegalArgumentException();

        this.nVariables=nVariables;
        this.literals=new ArrayList<>();
        for(int i=0;i<nVariables;++i)
            this.literals.add(-1);

        for(Integer x : literals)
            this.literals.set(Math.abs(x)-1, (x>0)?1:0);
    }


    public int getnVariables() {
        return nVariables;
    }




    public List<Integer> getLiterals() {
        return literals;
    }


    public List<Configuration> getConstraint() {
        List<Configuration> ret = new LinkedList<>();
        Configuration c1 = Configuration.getNegative(this,1);
        Configuration c2 = Configuration.getNegative(this, 0);
        
        ret.add(c1);
        if(c1.equals(c2))
            return ret;
            
        ret.add(c2);
        return ret;
    }


    @Override
    public String toString() {
        return "Clause [nVariables=" + nVariables + ", literals=" + literals + "]";
    }



}