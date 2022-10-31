package exercises.streams.esercitazione31102022;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import exercises.streams.itcompany_orig.ComputerProgrammer;

public class StreamQueries {
    public static void main(String[] args) {
        try {
            String globalPath = "/Users/teodorosullazzo/Documents/git_repos/College-courses/BigData/exercises/streams/itcompany_orig/ITCompany_10000.txt";
            Files.lines(new File(globalPath).toPath()).limit(100).map(x->ComputerProgrammer.parse(x))
                .forEach(x->System.out.println(x));

                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
