package exercises;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.Character;
 
public class Read {

    public static Map<Character,Integer> ref = new HashMap<>();
    public static int[][] ret = new int[4][26];
    public static void main(String[] args) throws IOException {
        
        String filename = "commedia.txt";
        int nproc=4;
        createMap(ref);
        System.out.println(ref);

        for(int[] x : ret) {
            Arrays.fill(x, 0);
        }
        long length=get_length(filename);
        long size_per_thread = length/nproc;

        long last_size=length-(size_per_thread*(nproc-1));
        long start = System.nanoTime();
        for(int i=0;i<nproc;++i) {
            if(i==nproc-1) 
                (new Reader(i, size_per_thread*i, last_size, filename)).run();
            else
                (new Reader(i, size_per_thread*i, size_per_thread, filename)).run();
        }

        long end = System.nanoTime();
        
        int[] total = new int[26];
        Arrays.fill(total, 0);
        for(int i=0;i<ret.length;++i) {
            System.out.println(Arrays.toString(ret[i]));
            for(int j=0;j<ret[0].length;++j)
                total[j]+=ret[i][j];
        }

        char a = 'a';
        for(int i=0;i<total.length;++i) {
            System.out.println(a+" occurrences : "+total[i]);
            a++;
        }

        long execution = (end - start);
        System.out.println("Execution time: " + execution + " nanoseconds");


        
        
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

    static class Reader extends Thread {
        private long start,size;
        private String filename;
        private int id;

        public Reader(int id, long start, long size, String filename){
            this.start=start;
            this.size=size;
            this.filename=filename;
            this.id=id;
        }
        public void run() {
            try {
                RandomAccessFile raf = new RandomAccessFile(new File(filename), "r");
                FileInputStream fis = new FileInputStream(raf.getFD());
                raf.seek(start);
                BufferedInputStream bif = new BufferedInputStream(fis);
                for(int i=0;i<size;++i) {
                    char read = Character.toLowerCase((char)bif.read());
                    if(! (read>='a' && read<='z')) continue;
                    int entry = ref.get(read);
                    ret[id][entry]++;
                }
                raf.close();

            } catch (Exception e) {
                e.printStackTrace();
            } 

        }
    }

}