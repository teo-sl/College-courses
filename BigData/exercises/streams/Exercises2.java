package BigData.exercises.streams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercises2 {
    public static void main(String[] args) {
        List<String> b1 = List.of("a","b","c");
        List<String> b2 = List.of("b","d","e");

        Student s1 = new Student(b1), s2 = new Student(b2);

        List<Student> students = List.of(s1,s2);

        List<String> res = students.stream()
                .flatMap(s->s.getBooks().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(res);

        Set<String> res2 = students.stream()
                .flatMap(s->s.getBooks().stream())
                .collect(Collectors.toSet());

        System.out.println(res2);

        Set<String> res3 = students.stream()
                .flatMap(s->s.getBooks().stream())
                .map(s->Set.of(s))
                .reduce(new HashSet<>(),(acc,el)->{acc.addAll(el); return acc;});
        
        System.out.println("res3: "+res3);

        
    }

    private static List<String> merge(List<String> a, List<String> b) {
        if(a.contains(b.get(0))) return a;
        a.add(b.get(0));
        return a;
    }

}

class Student {
    List<String> books;

    public Student(List<String> books) {
        this.books = List.copyOf(books);
    }

    public List<String> getBooks() {
        return this.books;
    }
    
}