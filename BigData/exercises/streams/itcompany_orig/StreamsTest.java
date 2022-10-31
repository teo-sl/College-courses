package exercises.streams.itcompany_orig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamsTest {
    public static void main(String[] args) {
        
        String globalPath = "/Users/teodorosullazzo/Documents/git_repos/College-courses/BigData/exercises/streams/itcompany_orig/ITCompany_10000.txt";
        List<ComputerProgrammer> cps = new ArrayList<>();
        try {
            cps = ComputerProgrammer.readFile(globalPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 1) Print first n employees

        System.out.println("\n 1) Print first n employees \n");

        cps.stream().limit(3).forEach(x->System.out.println(x));

        // 2) Number of employees

        System.out.println("\n 2) Number of employees \n");

        System.out.println("There are "+ cps.stream().count());

        // 3) Emoployees with salary >= 5000

        System.out.println("\n 3) Emoployees with salary >= 5000 \n");

        System.out.println("With salary >=5000: "+cps.stream().filter(x->x.getSalary()>=5000).count());


        // 4) Person with max salary

        System.out.println("\n 4) Person with max salary \n");

        long start = System.currentTimeMillis();
        Optional<String> maxPerson = cps.stream().max((a,b)->a.getSalary()-b.getSalary()).map(x->x.getName()+" "+x.getSurname()+" with salary "+x.getSalary());
        if(maxPerson.isPresent())
            System.out.println(maxPerson.get());

        long end = System.currentTimeMillis();
        System.out.println("execution time: "+(end-start));


        // 5) Somma stipendi per dipartimento

        System.out.println("\n 5) Somma stipendi per dipartimento \n");

        cps.stream().collect(Collectors.toMap(x->x.getDepartment(), x->x.getSalary(),(x,y)->x+y)).forEach((x,y)->
            System.out.println(x+" : "+y)
        );

        // 6) Per ogni skill dire quanti dipendenti sono presenti nell’azienda che possiedono tale skill.

        System.out.println("\n 6) Per ogni skill dire quanti dipendenti sono presenti nell’azienda che possiedono tale skill. \n");

        cps.stream().map(x->x.getSkills()).flatMap(x->x.stream())
                    .collect(Collectors.toMap(x->x,x->1,(a,b)->a+b))
                    .forEach((x,y)->System.out.println(x+" "+y));

        // 7) Per ogni dipartimento restituire la lista dei dipendenti (ID) che lavora in quel dipartimento.

        System.out.println("\n 7) Per ogni dipartimento restituire la lista dei dipendenti (ID) che lavora in quel dipartimento. \n");

        cps.stream().limit(100)
            .collect(Collectors.toMap(
                x->x.getDepartment(), x->List.of(x.getId()),
                (x,y)->mergeLists(x,y)))
        .forEach((x,y)->System.out.println(x+" : "+y));

        // 8) Per ogni skill dire quali dipendenti (ID) possiedono tale skill.

        System.out.println("\n 8) Per ogni skill dire quali dipendenti (ID) possiedono tale skill. \n");

        cps.stream().limit(100).map(x->x.getSkills().stream()
                .map(y->new Coppie<Integer,String>(x.getId(), y))
                .collect(Collectors.toList()))
            .flatMap(x->x.stream())
            .collect(Collectors.toMap(x->x.getSecondValue(), x->List.of(x.getFirtValue()),
                (x,y)->mergeLists(x, y)))
            .forEach((x,y)->System.out.println(x+" : "+y));

        // new version of the previous query

        System.out.println("\n check if equals to previous \n");

        cps.stream().limit(100).map(x->x.getSkills().stream()
                .map(y->List.of(x.getId()+"",y))
                .collect(Collectors.toList()))
            .flatMap(x->x.stream())
            .collect(Collectors.toMap(x->x.get(1), x->List.of(x.get(0)),
                (x,y)->{
                    List<String> ret = new ArrayList<>();
                    ret.addAll(x);
                    ret.addAll(y);
                    return ret;
                }))
            .forEach((x,y)->System.out.println(x+" : "+y));



        // 9) Dipartimenti dei dipendenti con top salary

        System.out.println("\n 9) Dipartimenti dei dipendenti con top salary \n");

        cps.stream().sorted((a,b)->b.getSalary()-a.getSalary()).limit(10).map(x->x.getDepartment())
            .forEach(x->System.out.println(x));


        // 10) primi 100 raggruppati per dipartimento e ordinare per i dipartimenti con più dipendenti

        System.out.println("\n 10) primi 100 raggruppati per dipartimento e ordinare per i dipartimenti con più dipendenti \n");


        cps.stream().sorted((a,b)->b.getSalary()-a.getSalary()).limit(100)
            .collect(Collectors.toMap(x->x.getDepartment(), x->1,
            (x,y)->x+1))
            .entrySet().stream()
	            .sorted(Map.Entry.comparingByValue((a,b)->b-a)) 			
	            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	            (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                
                .forEach((x,y)->System.out.println(x+" "+y));

        
        System.out.println("check if equals to last query");


        cps.stream().sorted((a,b)->b.getSalary()-a.getSalary()).limit(100)
                .collect(Collectors.toMap(x->x.getDepartment(), x->1,
                (x,y)->x+1))
                .entrySet().stream()
                .sorted((x,y)->y.getValue()-x.getValue())
                .forEach(x->System.out.println(x.getKey()+" "+x.getValue()));

    }

    public static List<Integer> mergeLists(List<Integer> a, List<Integer> b) {
        List<Integer> ret = new ArrayList<>();
        ret.addAll(a); ret.addAll(b);
        return ret;
    }


    
}

class Coppie<T,V> {
    private T firtValue;
    private V secondValue;
    public Coppie(T firtValue, V secondValue) {
        this.firtValue = firtValue;
        this.secondValue = secondValue;
    }
    public T getFirtValue() {
        return firtValue;
    }
    public V getSecondValue() {
        return secondValue;
    }
    
}
