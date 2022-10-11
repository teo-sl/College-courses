package BigData.exercises.streams;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsTest {
    public static void main(String[] args) throws IOException {
        int value = IntStream
            .range(0, 100)
            .map(x->x^2).sum();
        List<Integer> list = IntStream
            .range(0,100)
            .filter(x->x%2==0)
            .boxed()
            .collect(Collectors.toList());
        

        long size = IntStream
            .range(0,100)
            .filter(x->x%2==0)
            .count();

        int reduce = IntStream
            .range(0, 100)
            .reduce(0,(sub,el)->sub+el);

        System.out.println(value);
        System.out.println(list);
        System.out.println(size);
        System.out.println(reduce);

        System.out.println();

        List<Integer> positive = IntStream.range(-100, 100)
            .filter(x->x>=0)
            .boxed()
            .collect(Collectors.toList());
        System.out.println(positive);

        List<Integer> linesLength = Files.lines(Paths.get("./BigData/exercises/streams/test.txt"))
            .map(x->x.length())
            //.sorted()
            .collect(Collectors.toList());

        System.out.println(linesLength);
    }
}