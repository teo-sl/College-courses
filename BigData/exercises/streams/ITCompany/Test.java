package BigData.exercises.streams.ITCompany;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        ComputerProgrammer cp1 = new ComputerProgrammer(0, "Mario", "Rossi", "DIMES", 1500, Set.of("english","java","springboot","mysql"));
        ComputerProgrammer cp2 = new ComputerProgrammer(1, "Giovanni", "Verdi", "DIMEG", 11800, Set.of("english","autocad","linear","goal"));
        ComputerProgrammer cp3 = new ComputerProgrammer(2, "Ernesto", "Gialli", "DIMES", 1100, Set.of("english","python","angular","per"));
        ComputerProgrammer cp4 = new ComputerProgrammer(3, "Luigi", "Marroni", "DISU", 1900, Set.of("english","french","python","mysql"));
        ComputerProgrammer cp5 = new ComputerProgrammer(4, "Chiara", "Grigi", "DIBEST", 800, Set.of("english","lisp","c","postgress"));

        List<ComputerProgrammer> cps = List.of(cp1,cp2,cp3,cp4,cp5);


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

        String maxPerson = cps.stream().max((a,b)->a.getSalary()-b.getSalary()).map(x->x.getName()+" "+x.getSurname()).get();
        System.out.println(maxPerson);


        // 5) Somma stipendi per dipartimento

        System.out.println("\n 5) Somma stipendi per dipartimento \n");

        cps.stream().collect(Collectors.toMap(x->x.getDepartment(), x->x.getSalary(),(x,y)->x+y)).forEach((x,y)->
            System.out.println(x+" : "+y)
        );

        // 6) Per ogni skill dire quanti dipendenti sono presenti nell’azienda che possiedono tale skill.

        System.out.println("\n 6) Per ogni skill dire quanti dipendenti sono presenti nell’azienda che possiedono tale skill. \n");

        cps.stream().map(x->x.getSkills().stream().map(y->List.of(x.getId()+"",y)).collect(Collectors.toList()))
                    .flatMap(x->x.stream())
                    .collect(Collectors.toMap(x->x.get(1),x->1,(a,b)->a+b))
                    .forEach((a,b)->System.out.println(a+" "+b));

        // 7) Per ogni dipartimento restituire la lista dei dipendenti (ID) che lavora in quel dipartimento.

        System.out.println("\n 7) Per ogni dipartimento restituire la lista dei dipendenti (ID) che lavora in quel dipartimento. \n");

        cps.stream().collect(Collectors.toMap(
            x->x.getDepartment(), x->List.of(x.getId()),
            (x,y)->Stream.of(x,y)
                .flatMap(xx->xx.stream())
                .collect(Collectors.toList())))
            .forEach((x,y)->System.out.println(x+" : "+y));

        // 8) Per ogni skill dire quali dipendenti (ID) possiedono tale skill.

        System.out.println("\n 8) Per ogni skill dire quali dipendenti (ID) possiedono tale skill. \n");

        cps.stream().map(x->x.getSkills().stream()
                .map(y->List.of(x.getId()+"",y))
                .collect(Collectors.toList()))
            .flatMap(x->x.stream())
            .collect(Collectors.toMap(x->x.get(1), x->List.of(x.get(0)),
                (x,y)->Stream.of(x,y)
                    .flatMap(xx->xx.stream())
                    .collect(Collectors.toList())))
            .forEach((x,y)->System.out.println(x+" : "+y));



        // 9) Dipartimenti dei dipendenti con top salary

        System.out.println("\n 9) Dipartimenti dei dipendenti con top salary \n");

        cps.stream().sorted((a,b)->b.getSalary()-a.getSalary()).limit(2).map(x->x.getDepartment())
            .forEach(x->System.out.println(x));


        // 10) primi 100 raggruppati per dipartimento e ordinare per i dipartimenti con più dipendenti

        System.out.println("\n 10) primi 100 raggruppati per dipartimento e ordinare per i dipartimenti con più dipendenti \n");

        cps.stream().sorted((a,b)->b.getSalary()-a.getSalary()).limit(5)
            .collect(Collectors.toMap(x->x.getDepartment(), x->1,
            (x,y)->x+1))
            .entrySet().stream()
	            .sorted(Map.Entry.comparingByValue((a,b)->b-a)) 			
	            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	            (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                
                .forEach((x,y)->System.out.println(x+" "+y));
            

        
    }
    
}
