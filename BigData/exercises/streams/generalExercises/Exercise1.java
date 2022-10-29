package exercises.streams.generalExercises;

import java.util.stream.IntStream;

public class Exercise1 {
    public static void main(String[] args) {
        // sum of abs
        int sum = IntStream.range(-50, 50)
            .map(Math::abs)
            .sum();

        System.out.println(sum);


        // largest even

        int largest = IntStream.range(-50, 51)
            .filter(x->x%2==0)
            .max()
            .getAsInt();
        System.out.println(largest);
    }
    
}
