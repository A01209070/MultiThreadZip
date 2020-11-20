import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip
{
	
	/**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    
    /**
     * Extracts a zip file specified by the path to a directory specified by
     * directory (will be created if does not exists)
     * @param path
     * @param directory
     * @throws IOException
     */
    public void UnZipper(String path, String directory) throws IOException{
    	File destination = new File(directory);
    	//If the destination folder does not exists, create it
    	if(!destination.exists()) {
    		destination.mkdir();
    	}
    	ZipInputStream read = new ZipInputStream(new FileInputStream(path));
    	//Get the first entry of the zip
    	ZipEntry entry = read.getNextEntry();
    	//Loop while there are files to unzip
    	while(entry != null) {
    		//Determine the path for the file
    		String newPath = directory + File.separator + entry.getName();
    		if(!entry.isDirectory()) {
    			//If it is a file
    			ExtractFile(read, newPath);
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
    private void ExtractFile(ZipInputStream read, String path) throws IOException{
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
	
	public static void main(String[] args) throws IOException {
        String[] myFiles = { "C:/Users/anira/Desktop/Documentos.zip" , "C:/Users/anira/Desktop/CERTIFICATEOFCOMPLETION.zip", "C:/Users/anira/Desktop/Componente1.zip"};
		String[] zipFile = {  "C:/Users/anira/Desktop/Documentos", "C:/Users/anira/Desktop/CERTIFICATEOFCOMPLETION", "C:/Users/anira/Desktop/Componente1"};
		
		Unzip unzipper = new Unzip();
		
		//Initialize the timer for the execution
        long start = System.currentTimeMillis();
        
        for(int i = 0; i < myFiles.length; i++) {
        	try {
                unzipper.UnZipper(myFiles[i], zipFile[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
      //Calculate the time consumed by the GPU
      float seconds = (System.currentTimeMillis() - start) / 1000f;
      System.out.println("The zipping time for all files was: " + seconds + " seconds.");
      System.out.println("Completed Successfully");
    }
}

