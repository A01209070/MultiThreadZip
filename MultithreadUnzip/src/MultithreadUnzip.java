import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MultithreadUnzip implements Runnable{
	/**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    
  //Attributes of the class
  	private String path;
  	private String directory;
  	
  	/**
	 * Constructor of the class to unzip the file that in the specified directory
	 * @param path Path that specifies the file to unzip
	 * @param directory The path to the destination directory to store the unzipped file
	 */
  	public MultithreadUnzip(String path, String directory) {
  		this.path = path;
  		this.directory = directory;
  	}
    
    /**
     * Extracts a zip file specified by the path to a directory specified by
     * directory (will be created if does not exists)
     * @param path
     * @param directory
     * @throws IOException
     */
    public void unzip() throws IOException{
    	File destination = new File(this.directory);
    	//If the destination folder does not exists, create it
    	if(!destination.exists()) {
    		destination.mkdir();
    	}
    	ZipInputStream read = new ZipInputStream(new FileInputStream(this.path));
    	//Get the first entry of the zip
    	ZipEntry entry = read.getNextEntry();
    	//Loop while there are files to unzip
    	while(entry != null) {
    		//Determine the path for the file
    		String newPath = this.directory + File.separator + entry.getName();
    		if(!entry.isDirectory()) {
    			//If it is a file
    			extractFile(read, newPath);
    		}else {
    			//If it is a directory
    			File newDir = new File(newPath);
    			newDir.mkdirs();
    		}
    		read.closeEntry();
    		entry = read.getNextEntry();
    	}
    	read.close();
    }
    
    
    /**
     * Extracts a zip entry or file entry
     * @param read
     * @param path
     * @throws IOException
     */
    private void extractFile(ZipInputStream read, String path) throws IOException{
    	//Read the file
    	BufferedOutputStream bufferOS = new BufferedOutputStream(new FileOutputStream(path));
    	byte[] buffer = new byte[BUFFER_SIZE];
    	int bytesRead = 0;
    	//While reading the file
    	while((bytesRead = read.read(buffer)) != -1) {
    		//Write the read bytes in the file
    		bufferOS.write(buffer, 0, bytesRead);
    	}
    	bufferOS.close();
    }
    
    public void run() {
		//Initialize the timer for the execution
        long start = System.currentTimeMillis();
		try {
			unzip();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Calculate the time consumed by the GPU
		float seconds = (System.currentTimeMillis() - start) / 1000f;
		System.out.println("The zipping time for file \"" + this.path + "\" was: " + seconds + " seconds.");
		System.out.println("Completed Successfully");
	}
}
