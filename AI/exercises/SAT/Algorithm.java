package exercises.SAT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Algorithm {

    private static int nVar = 252;


    public static void main(String[] args) {

        /*
        List<Integer> c1 = convertToConfig(List.of(-1,-2,-3));
        List<Integer> c2 = convertToConfig(List.of(-1,-3));
        List<Integer> c3 = convertToConfig(List.of(-2,-3));
        //List<Integer> c4 = convertToConfig(List.of(-1,-2));
        */
        List<List<Integer>> raw = readFile(1);
        List<List<Integer>> toSolve = raw.stream().map(x->convertToConfig(x)).collect(Collectors.toList());
        List<Integer> sigma = solve1(toSolve);
        System.out.println(sigma);

        boolean ret = verifyAssignement(sigma, raw);
        System.out.println(ret);

    }



    private static List<Integer> convertToConfig(List<Integer> l) {
        Integer[] tmp = new Integer[nVar];
        Arrays.fill(tmp, -1);
        List<Integer> ret = Arrays.asList(tmp);

        int idx;
        for(Integer x : l) {
            idx = Math.abs(x)-1;
            ret.set(idx, (x>0)?1:0);
        }

        return ret;
        
    }

    private static List<List<Integer>> getConstraints(List<Integer> clause) {
        List<Integer> first,second;
        List<List<Integer>> ret = new LinkedList<>();
        first = clause.stream().map(x->(x!=1 && x!=0)?0:(x+1)%2).collect(Collectors.toList());
        second = clause.stream().map(x->(x!=1 && x!=0)?1:(x+1)%2).collect(Collectors.toList());

        ret.add(first);
        if(!first.equals(second)) ret.add(second);

        return ret;        
    }
    private static int compareConfiguration(List<Integer> a, List<Integer> b) {
        for(int i=0;i<a.size();++i)
            if(a.get(i)!=b.get(i))
                return a.get(i)-b.get(i);
        return 0;
    }
    private static List<Integer> getSuccessor(List<Integer> c) {
        List<Integer> ret = new ArrayList<>(c);
        int last = c.size()-1;
    
        if(c.get(last)==0) {
            ret.set(last, 1);
            return ret;
        }
        
        ret.set(last, 0);

        for(int i=c.size()-2;i>=0;--i) {
            if(c.get(i)==0) {
                ret.set(i, 1);
                return ret;
            }
            else 
                ret.set(i,0);
        }

        return ret;
    
    }

    private static List<Integer> solve1(List<List<Integer>> clauses) {

        List<List<Integer>> denied = new LinkedList<>();

        for(List<Integer> c : clauses) {
            denied.addAll(getConstraints(c));
        }
        denied = denied.stream().distinct().sorted((a,b)->compareConfiguration(a,b)).collect(Collectors.toList());


        
        if(denied.get(0).stream().filter(x->x!=0).count()!=0) return new ArrayList<>(Collections.nCopies(nVar, 0));

        List<Integer> successor;
        for(int i=0;i<denied.size()-1;++i) {
            successor = getSuccessor(denied.get(i));
            System.out.println(successor+"\n"+denied.get(i+1)+"\n\n");
            if(!denied.get(i+1).equals(successor)) return successor;   
        }
        return null;

    }

    public static List<List<Integer>> readFile(int i) {
        List<List<Integer>> ret = new LinkedList<>();
        String global = "/Users/teodorosullazzo/Documents/git_repos/College-courses/AI/exercises/SAT/clauses";
        String path = global+"/uf250-0"+i+".cnf";
        File f = new File(path);
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line = r.readLine();
            while(line!=null) {
                line=line.trim();
                String[] parts = line.split(" ");

                
                line = r.readLine();

                
                if(parts[0].equals("c") || parts[0].equals("p") || parts.length<4) continue;
                ret.add(List.of(Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]),
                                Integer.parseInt(parts[2])
                        ));
            }
            r.close();
    
        } catch (Exception e) {
           
            e.printStackTrace();
        }

        return ret;
        
    }

    public static boolean verifyAssignement(List<Integer> assignement, List<List<Integer>> formula) {
        int i = 1;
        for(List<Integer> c : formula) {
            if(c.stream().map(x->(x>0)? assignement.get(Math.abs(x)) : (assignement.get(Math.abs(x))+1)%2 ).filter(x->x>0).count()==0) {System.out.println(i); return false;}
            i++;
        }

        return true;
    }
}
