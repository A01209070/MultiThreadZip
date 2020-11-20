import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;

public class MultithreadZipMain {
	
	//Define the number of threads in the thread pool
	private static int MAX_THREADS = 20;
	
	public static void main(String args[]) throws FileNotFoundException, IOException, InterruptedException {
		//C:\Users\anira\Desktop\Pruebas\Results
		
		int numFiles;
		System.out.print("Introduce the Number of Files to Zip: ");
		Scanner sc = new Scanner(System.in);
		numFiles = sc.nextInt();
		String[] myFiles = new String[numFiles];
		System.out.println("Introduce the Absolute Path of Each File to Zip: ");
		for(int i = 0; i < numFiles; i++) {
			myFiles[i] = sc.next();
			//System.out.println(myFiles[i]);
		}
		System.out.println("Introduce the Absolute Path of the Destination Folder of All Files to Zip: ");
		String zipFile = sc.next();
		//System.out.println(zipFile);
		
		// Thread pool with MAX_THREADS number of threads as the fixed pool size(Step 2) 
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);   

		for(int i = 0; i < myFiles.length; i++) {
			//Pass the runnable object to the pool to execute
			pool.execute(new MultithreadZip(myFiles[i], zipFile + "/test" + (i + 1) + ".zip"));
		}
		
		//Avoid receiving new tasks
		pool.shutdown();	
	}
}
