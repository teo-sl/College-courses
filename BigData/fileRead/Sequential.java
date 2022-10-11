package fileRead;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Sequential {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        int alphabetSize=26;
        int[] occurrences = new int[alphabetSize];
        Arrays.fill(occurrences,0);
        
        Map<Character,Integer> ref = new HashMap<>();
        
        createMap(ref);

        String filename = "commedia.txt";


        long size=get_length(filename);

        RandomAccessFile raf = new RandomAccessFile(new File(filename), "r");
        FileInputStream fis = new FileInputStream(raf.getFD());
        BufferedInputStream bif = new BufferedInputStream(fis);
        for(int i=0;i<size;++i) {
            char read = Character.toLowerCase((char)bif.read());
            if(! (read>='a' && read<='z')) continue;
            int entry = ref.get(read);
            occurrences[entry]++;
        }
        raf.close();
        long end = System.nanoTime();
        long execution = (end - startTime);
        System.out.println("Final result : \n"+Arrays.toString(occurrences));

        System.out.println("Time of execution: "+execution);


        FileWriter fw = new FileWriter("sequentialResult.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        pw.println(execution+", "+size);

        pw.close();
    }


    private static void createMap(Map<Character,Integer> ref) {
        int i=0;
        for(char x = 'a';x<='z';++x) {
            ref.put(x,i);
            i++;
        }
    }

    private static long get_length(String fileName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(new File(fileName), "r");
        long ret = raf.length();
        raf.close();
        return ret;
        

    }
    
}