
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.*;

public class Zip {
	
	/**
	 * Constant for the buffer size that is used to read and write data
	 */
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * Method to compress a list of files in a destination file
	 * @param list The list of all the files and directories to compress
	 * @param destinationZip The path to the destination of the zip file
	 * @throws FileNotFountException
	 * @throws IOException
	 */
	public void zip(List<File> list, String destinationZip) throws FileNotFoundException, IOException{
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
	 * Zips the files that are stored in the paths stored
	 * @param files String array to stores the paths of the files to zip
	 * @param destinationZip The path to the destination of the zip file
	 * @throws FileNotFountException
	 * @throws IOException
	 */
	public void zip(String[] files, String destinationZip) throws FileNotFoundException, IOException{
		List<File> list = new ArrayList<File>();
		for(int i = 0; i < files.length; i++) {
			list.add(new File(files[i]));
		}
		zip(list, destinationZip);
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
     * @throws IOException
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
	
	
	
	public static void main(String[] args) {
		//C:\Users\anira\Desktop\Pruebas\SequantialResults\ZipAll.zip
		Zip zip = new Zip();
		
		int numFiles;
		System.out.print("Introduce the Number of Files to Zip: ");
		Scanner sc = new Scanner(System.in);
		numFiles = sc.nextInt();
		String[] myFiles = new String[numFiles];
		System.out.println("Introduce the Absolute Path of Each File to Zip: ");
		for(int i = 0; i < numFiles; i++) {
			myFiles[i] = sc.next();
		}
		System.out.println("Introduce the Absolute Path of the Destination Folder of All Files to Zip: ");
		String zipFile = sc.next();
		
		long start = System.currentTimeMillis();
		try {
			zip.zip(myFiles, zipFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		float seconds = (System.currentTimeMillis() - start) / 1000f;
		System.out.println("The zipping time was: " + seconds + " seconds.");
		System.out.println("Completed Successfully");
	}
}
