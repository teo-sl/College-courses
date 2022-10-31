package exercises.streams.itcompany_orig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDM {
    public static void main(String[] args) throws IOException {
        String globalPath = "/Users/teodorosullazzo/Documents/git_repos/College-courses/BigData/exercises/streams/itcompany_orig/ITCompany_10000.txt";
        List<ComputerProgrammer> cps = new ArrayList<>();
        try {
            cps = ComputerProgrammer.readFile(globalPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cps.stream().sorted((a,b)->b.getSalary()-a.getSalary()).limit(100)
                //.peek(x->System.out.println(x))
                .map(x->x.getDepartment())
                .collect(Collectors.toMap(x->x,x->1,(a,b)->a+b))
                .forEach((x,y)->System.out.println(x+" : "+y));


        double max = cps.stream().max((a,b)->a.getSalary()-b.getSalary()).map(x->x.getSalary()).get();
        System.out.println(max);
        Set<String> skills = cps.stream().map(x->x.getSkills()).flatMap(x->x.stream())
                    .collect(Collectors.toSet());

        System.out.println(skills);

        cps.stream().map(x->x.getSkills().stream()
                .map(y->List.of(x.getSalary()+"",y))
                .collect(Collectors.toList()))
            .flatMap(x->x.stream())
            .collect(Collectors.toMap(x->x.get(1), x->List.of(Integer.parseInt(x.get(0))),
                (x,y)->Stream.of(x,y)
                    .flatMap(xx->xx.stream())
                    .collect(Collectors.toList())));
           // .forEach((x,y)->System.out.println(x+" : "+(y.stream().reduce(0, (xx,yy)->xx+yy)/y.size())));


        System.out.println(cps.stream().max((a,b)->a.getSalary()-b.getSalary())
            .get());
        /*
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("./res.csv"))));
        for(Double x : res)
            bw.write(x+"\n");
        bw.close();
         */

         /*
          * In conclusione, pare che gli aspetti più determinanti per lo stipendio di
          * di un dipendente siano 
          * -- il dipartimento di appartenenza
          * -- le skill a disposizione
          * 
          * In particolare, pare che, chi appartenga al dipartimento A riceva stipendi
          * più alti, e che la skill maggiormente pagata sia Spark
          */
    }
    
}
