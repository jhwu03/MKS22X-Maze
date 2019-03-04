import java.util.*;
import java.io.*;
public class Maze{


    private char[][]maze;
    private boolean animate;//false by default
    private int[][] mazeMove = {{0, -1},{-1, 0},{0, 1},{1, 0}};

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

         throw a FileNotFoundException or IllegalStateException

    */


    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
        animate = false;
        File text = new File(filename);
        Scanner s = new Scanner(text);
        String line = "";
        while(s.hasNextLine()){
          line = s.nextLine();
          line = "\n";
        }
        int row = 0;
        int col = 0;
        for(int i = 0; i < line.length() ; i++){
          if(line.charAt(i) == '\n'){
            row++;
          }
        }
        col = line.length() / row;
        maze = new char[row][col];
        int index = 0;
        for(int r = 0;r <maze.length; r++){
          for(int c = 0; c < maze[0].length; c++){
            maze[r][c]= line.charAt(index);
            index++;
          }
        }
        int E = 0;
        int S = 0;
        Scanner read = new Scanner(text);
        for(int i = 0; i < row; i++){
          String lineS = read.nextLine();
          for(int y = 0; y < col; y++){
            if(lineS.charAt(y) == 'E') E++; //check number of E
            if(lineS.charAt(y) == 'S') S++; //check number of S
            maze[i][y] = line.charAt(y);
          }
        }
        if(E != 1 || S != 1) throw new IllegalStateException();
      }


    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){

        animate = b;

    }


    public void clearTerminal(){

        //erase terminal, go to top left of screen.

        System.out.println("\033[2J\033[1;1H");

    }






   /*Return the string that represents the maze.

     It should look like the text file with some characters replaced.

    */
    public String toString(){
        String ans = "";
        for(int i = 0; i < maze.length; i++){
          for(int y = 0; y < maze[i].length; y++){
            ans += maze[i][y] + "";
            if(y == maze[i].length - 1) ans += "\n";
          }
        }
        return ans;
    }

    public boolean addMove(int row, int col){
      if(row >= 0 && row < maze.length && col >= 0 && col < maze[0].length){
       if(maze[row][col] == ' '){
         maze[row][col] = '@'; //place @
         return true;
        }
      }
        return false;
    }

    private boolean removeMove(int row, int col){
      if(maze[row][col] != '@') return false; //checks if the square was marked
      maze[row][col] = '.'; //places .
      return true;
    }



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){
      int corXOfS = 0;
      int corYOfS = 0;
      for(int i = 0; i < maze.length; i++){
        for(int r = 0; r < maze[i].length; r++){
          if(maze[i][r] == 'S'){
            corXOfS = i;
            corYOfS = r;
            maze[i][r] = ' '; //erase the S
          }
        }
      }
            //find the location of the S.
            //erase the S
            //and start solving at the location of the s.
            //return solve(???,???);
      return solve(corXOfS, corYOfS, 0);
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col, int moveNumber){ //you can add more parameters since this is private

      int ans;
        //automatic animation! You are welcome.
        if(animate){

            clearTerminal();
            System.out.println(this);

            wait(20);
        }
        if(maze[row][col] == 'E'){
          return moveNumber;
        }
        if(!addMove(row, col)){
          return -1;
        }
        for(int i = 0; i < mazeMove.length; i++){
          ans = solve(row + mazeMove[i][0], col + mazeMove[i][1], moveNumber + 1);
        if(ans != -1){
          return ans;
        }
      }
      removeMove(row,col);
      return -1;
    }
  }
