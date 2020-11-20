import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadUnzipMain {
	//Define the number of threads in the thread pool
	private static int MAX_THREADS = 5;
		
	public static void main(String args[]) throws FileNotFoundException, IOException, InterruptedException {
		String[] myFiles = { "C:/Users/anira/Desktop/Documentos.zip" , "C:/Users/anira/Desktop/CERTIFICATEOFCOMPLETION.zip", "C:/Users/anira/Desktop/Componente1.zip"};
		String[] zipFile = {  "C:/Users/anira/Desktop/Documentos", "C:/Users/anira/Desktop/CERTIFICATEOFCOMPLETION", "C:/Users/anira/Desktop/Componente1"};
		
		// Thread pool with MAX_THREADS number of threads as the fixed pool size(Step 2) 
	    ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);   

		for(int i = 0; i < myFiles.length; i++) {
			//Pass the runnable object to the pool to execute
			pool.execute(new MultithreadUnzip(myFiles[i], zipFile[i]));// + "/test-" + (i + 1)
		}
			
		//Avoid receiving new tasks
		pool.shutdown();	
	}
}
