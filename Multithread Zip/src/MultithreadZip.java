import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MultithreadZip implements Runnable{
	
	/**
	 * Constant for the buffer size that is used to read and write data
	 */
	private static final int BUFFER_SIZE = 4096;
	
	//Attributes of the class
	private List<File> list;
	private String filename;
	private String destinationZip;
	
	/**
	 * Constructor of the class to zip the files that are stored in the paths
	 * @param files String array to stores the paths of the files to zip
	 * @param destinationZip The path to the destination of the zip file
	 */
	public MultithreadZip(String file, String destinationZip){
		this.filename = file;
		this.list = new ArrayList<File>();
		this.list.add(new File(file));
		this.destinationZip = destinationZip;
	}
	
	/**
	 * Method to compress a list of files in a destination file
	 * @param list The list of all the files and directories to compress
	 * @param destinationZip The path to the destination of the zip file
	 * @throws FileNotFountException
	 * @throws IOException
	 */
	private void zip(List<File> list, String destinationZip) throws FileNotFoundException, IOException{
		FileOutputStream destinationZipFile = new FileOutputStream(destinationZip);
		ZipOutputStream zipOS = new ZipOutputStream(destinationZipFile);
		for(File file : list) {
			if(file.isFile()) {
				zipFile(file, zipOS);
			}else {
				zipDirectory(file, file.getName(), zipOS);
			}
		}
		
	}
	
	/**
     * Zip all the files and directories inside the selected directory
     * @param folder Directory to add
     * @param parentDestinationZip Path of parent directory
     * @param zipOS Zip output stream
     * @throws FileNotFoundException
     * @throws IOException
     */
	private void zipDirectory(File folder, String parentDestinationZip, ZipOutputStream zipOS) throws FileNotFoundException, IOException{
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				zipDirectory(file, parentDestinationZip + "/" + file.getName(), zipOS);
				continue;
			}
			zipOS.putNextEntry(new ZipEntry(parentDestinationZip + "/" + file.getName()));
            BufferedInputStream bufferIS = new BufferedInputStream(new FileInputStream(file));
            
            long bytesRead = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = 0;
            while ((read = bufferIS.read(buffer)) != -1) {
            	zipOS.write(buffer, 0, read);
                bytesRead += read;
            }
            zipOS.closeEntry();
		}
	}
	
	/**
     * Adds a file to the current zip output stream
     * @param file the file to be added
     * @param zos the current zip output stream
     * @throws FileNotFoundException
     */
    private void zipFile(File file, ZipOutputStream zipOS) throws FileNotFoundException, IOException {
    	zipOS.putNextEntry(new ZipEntry(file.getName()));
        BufferedInputStream bufferIS = new BufferedInputStream(new FileInputStream(file));
        long bytesRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = bufferIS.read(buffer)) != -1) {
        	zipOS.write(buffer, 0, read);
            bytesRead += read;
        }
        zipOS.closeEntry();
    }
    
	public void run() {
		//Initialize the timer for the execution
        long start = System.currentTimeMillis();
		try {
			zip(this.list, this.destinationZip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Calculate the time consumed by the GPU
		float seconds = (System.currentTimeMillis() - start) / 1000f;
		System.out.println("The zipping time for file \"" + this.filename + "\" was: " + seconds + " seconds.");
		System.out.println("Completed Successfully");
	}
}
