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

import mpi.*;


public class ReadMPI {
    public static void main(String[] args) throws MPIException, IOException{
        
        // Start counting time
        long startTime = System.nanoTime();

        // Setting up variables
        int alphabetSize=26;
        int nProc = 4;
        String filename = "commedia.txt";
        long length = getFileLength(filename);

        // Setting the file info variables
        //long length=getFileLength(filename);
        long sizePerProc = length/nProc;
        long lastSize=length-(sizePerProc*(nProc-1));
    
        // Starting MPI envirorment
        MPI.Init(args);

        Comm comm = MPI.COMM_WORLD;
        int myrank = comm.getRank();

        if(nProc!=comm.getSize()) throw new IllegalArgumentException("Number of processes is different");

        long start = sizePerProc*myrank;
        
        int[] occurrences = new int[alphabetSize];

        try {
            RandomAccessFile raf = new RandomAccessFile(new File(filename), "r");
            FileInputStream fis = new FileInputStream(raf.getFD());
            raf.seek(start);
            BufferedInputStream bif = new BufferedInputStream(fis);
            
            long size; 
            if(myrank==nProc-1)
                size = lastSize;
            else
                size = sizePerProc; 
                
            for(int i=0;i<size;++i) {
                char read = Character.toLowerCase((char)bif.read());
                if(! (read>='a' && read<='z')) continue;
                int entry = read -'a';
                occurrences[entry]++;
            }
            raf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(myrank==0) {
            //System.out.println("Proc: "+myrank+"\n"+Arrays.toString(occurrences));
            int[] msg = new int[alphabetSize];
            for(int i=1;i<nProc;++i) {
                comm.recv(msg, msg.length, MPI.INT, MPI.ANY_SOURCE, 42);
                for(int j=0;j<alphabetSize;++j)
                    occurrences[j]+=msg[j];
            }

            long end = System.nanoTime();

            System.out.println("Final result: \n "+ Arrays.toString(occurrences));
            long execution = (end - startTime);
            System.out.println("Time of execution : "+execution);

            FileWriter fw = new FileWriter("mpiResult.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(execution+", "+length);

            pw.close();

        }else {
            //System.out.println("Proc: "+myrank+"\n"+Arrays.toString(occurrences));
            comm.send(occurrences, occurrences.length, MPI.INT, 0, 42);
            //System.out.println(myrank+" send message");
        }
        

        MPI.Finalize();
    }



    private static long getFileLength(String fileName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(new File(fileName), "r");
        long ret = raf.length();
        raf.close();
        return ret;
        

    }

}