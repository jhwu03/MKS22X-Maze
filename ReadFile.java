import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.Scanner ;

public class ReadFile {
  public static void main(String[] args) {
    try {
      File f = new File("Maze1.txt") ;
      Scanner s = new Scanner(f) ;
      while (s.hasNextLine()) {
        String line = s.nextLine() ;
        System.out.println(line) ;
      }
    } catch (FileNotFoundException e) {
      System.out.println("The file was not found!") ;
    }
    catch (Exception e) {
      System.out.println("An exception has occured") ;
    }

  }
}
