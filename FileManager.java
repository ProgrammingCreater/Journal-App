package CompSciIA;

import java.io.BufferedReader;
import java.io.File; 
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;   // Import the FileWriter class

/**
 * * The FileManager class provides functionality to manage file operations such as creating files,
 * writing to files, and reading from files in spcific directories
*/
public class FileManager{  
    
    private String fileName;
    private String directoryName;
    
    /**
     * Constructor to initialize the FileManager with just a file name
     * Directory name is set based upon the first 4 characters of the file name
     * 
     * @param fileName the name of the file 
     */
    public FileManager(String fileName)
    {
        this.fileName = fileName + ".txt";
        directoryName = fileName.substring(0,4);
    }

    /**
     * Constructor to initialize the FileManager with a file name and a specific directory.
     * 
     * @param fileName the name of the file (without extension)
     * @param directory the name of the directory where the file will be stored
     */
    public FileManager(String fileName, String directory)
    {
        this.fileName = fileName;
        directoryName = directory;
    }
    /**
     * Gets the directory name where the file is located or will be created.
     * 
     * @return the directory name
     */
    public String getDirectory()
    {
        return directoryName;
    }
     /**
     * Gets the file name of the file managed by this FileManager.
     * 
     * @return the file name
     */
    public String getFileName()
    {
        return fileName;
    }
     /**
     * Creates a new file in the specified directory. If the directory does not exist, it is created.
     * 
     * @return an empty string if the file is successfully created, "409" if the file already exists,
     *         or an error message if an exception occurs.
     */
    public String createFile()
    {
        // Create a File object 
        File directory = new File(directoryName);

        // Check if directory exists, if not then create it
        if(!(directory.exists() && directory.isDirectory()))
        {
            directory.mkdirs();
        }
        
        try {  
            // Create a File object 
          File myObj = new File(directory, fileName);  
          
          // If file is successfully created print its absolute path and return an empty string
          if (myObj.createNewFile()){  
            System.out.println("File Created: " + myObj.getAbsolutePath());
            return ("");  
          } 
          // Return an error message
          else {  
            return ("409");  
          }  
        } 
        // If an exception occurs while creating the file 
        catch (IOException e) {
            // Print the stack trace and return an error message
            e.printStackTrace();
            return ("An error occurred, while trying to create");
        }
    }
     /**
     * Writes a message to the file. If the file does not exist, it is created.
     * The message is appended to the end of the file.
     * 
     * @param message the message to write to the file
     */
    public void WriteToFile(String message) {
        try {
            File file = new File(directoryName, fileName);
            // the true at the end enables you to append to it
          FileWriter myWriter = new FileWriter(file, true);
          myWriter.write(message + "\n");
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
        } 
        catch (IOException x) {
            createFile();
            WriteToFile(message);  
            System.out.println("An error occurred, while trying to write.");
            x.printStackTrace();
        }
    }
    /**
     * Reads the content of a file and returns it as a string.
     * 
     * @param file the file to read
     * @return the content of the file as a string
     * @throws IOException if an error occurs while reading the file
     */
    String ReadFile (File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}