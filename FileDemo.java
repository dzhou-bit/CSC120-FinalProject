import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.BufferedWriter; // Import this class to write to a file

public class FileDemo {
  public static void main(String[] args) {
    System.out.println("***************************************");
    System.out.println("Learning goals for CSC120 (Spring 2024)");
    System.out.println("***************************************");
    try {
      File myFile = new File("CSC120-LGs.txt");
      Scanner fileReader = new Scanner(myFile); // <- Same kind of object we used to read from the command line! But instead of System.in, we're reading from the file

      // Loop until we run out of lines
      while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        System.out.println(" + " + data);
      }

      // Tidy up
      fileReader.close();
      
    } catch (FileNotFoundException e) { // Sometimes things go wrong; we'll handle that here
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // Writing to files
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt")); // Careful! This will overwrite any previous contents
      writer.write("Tada! We wrote to a file!");
      writer.close();
    } catch (IOException e) {
      System.out.println(e); // print error message
    }

  }
}