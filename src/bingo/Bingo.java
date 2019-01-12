/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo;
import java.io.*;
import java.util.Random;
import java.util.*;
/**
 *
 * @author lizziekim
 */
public class Bingo {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        Random rand = new Random();
        InputStreamReader inStream = new InputStreamReader( System.in ) ;
        BufferedReader stdin = new BufferedReader( inStream );
        
        int bingo[][] = new int [5][5];
        ArrayList<Integer> Total = new ArrayList<Integer>(); //complete numbers of 1-75
        populateArrays(1, 75, Total);
        
        ArrayList<Integer> B = new ArrayList<Integer>(); //to make sure no numers are repeated in B column
        populateArrays(1, 15, B);
        
        ArrayList<Integer> I = new ArrayList<Integer>(); //to make sure no numers are repeated in I column
        populateArrays(16, 30, I);
        
        ArrayList<Integer> N = new ArrayList<Integer>(); //to make sure no numers are repeated in N column
        populateArrays(31, 45, N);
        
        ArrayList<Integer> G = new ArrayList<Integer>(); //to make sure no numers are repeated in G column
        populateArrays(46, 60, G);
        
        ArrayList<Integer> O = new ArrayList<Integer>(); //to make sure no numers are repeated in O column
        populateArrays(61, 75, O);
        
        makeRow(bingo, 1, 15, 0, B); //Make the actual rows
        makeRow(bingo, 16, 30, 1, I);
        makeRow(bingo, 31, 45, 2, N);
        makeRow(bingo, 46, 60, 3, G);
        makeRow(bingo, 61, 75, 4, O);
        
       
        displayBoard(bingo); //display the board in the beginning
        
        System.out.println();
        
        int  n = 0;
        int countTurns = 0; //So you can tell the user how many times it took for them to win
        String answer; //This user prompts to move on
        do
        {   
            
            System.out.println("Enter enter to continue: "); //This is how the user can move on
            answer = stdin.readLine();
            
            if (answer.equals("")) //It must be the enter for the user to move on
            {
                countTurns += 1;
                int properNum = 0;
                for (int i = 0; i < 5; i++)
                {
                    n = rand.nextInt(75) + 1; //This is the random number drawn to see if it is on the board
                    if (Total.indexOf(n) >= 0)
                    {
                        properNum = n;
                        Total.remove(Total.indexOf(n)); //So repeating numbers arent drawn
                        break;
                    }
                    else
                    {
                        i--; //If the number was repeated then go back so that you can get another number
                    }
                }  
                
                System.out.println("The random number chosen is: " + n);
                for (int i = 0; i < bingo.length; i++)
                {
                    for (int j = 0; j < bingo[0].length; j++)
                    {
                        if (bingo[i][j] == n)
                        {
                            bingo[i][j] = 0; //remove if the number was picked
                        
                        }
                    }
                }
                displayBoard(bingo); //Display the bingo again, but with 0 in place of removed numbers
            }
            else
            {
                System.out.println("Please press enter: "); //if the user enters something else
            }
            System.out.println();
        } while (checkWin(bingo) == false); //If the user still has not won, repeat 
        
        if (checkWin(bingo) == true)
        {
            System.out.println();
            System.out.println("Congrats, you won!");  //Tell them if they won
            System.out.println("This took you: " + countTurns + " turns!"); //Tell them how many times it took
        }
        
    }
    
    public static void displayBoard(int bingo[][]) //This displays the board each time
    {
        for (int i = 0; i < bingo.length; i++)
        {
            for (int j = 0; j < bingo[0].length; j++)
            {
                if (bingo[i][j] >= 10)
                {
                    System.out.print(bingo[i][j] + " "); //printing out so that it lines up (instance, if its less than 10 then its only one digit)
                }
                else
                {
                    System.out.print(bingo[i][j] + "  "); //printing out so that it lines up
                }
            }
            System.out.println(); //Print so that its in a square
        }
    }  
    public static void populateArrays(int start, int end, ArrayList<Integer> A)
    {
        for (int i = start; i <= end; i++)
        {
            A.add(i); //this is making the arraylists for the checks
        }
    }
    public static boolean checkWin(int bingo[][])
    {
        int count = 0;
        int countTwo = 0;
        int countThree = 0;
        int countFour = 0;
        for (int i = 0; i < bingo.length; i++)
        {
            for (int j = 0; j < bingo[0].length; j++)
            {
                if (bingo[i][j] == 0)
                {
                    count++; //check for rows
                }
                if (bingo[j][i] == 0)
                {
                    countTwo++; //check for columns
                }
            }
            if (count == 5 || countTwo == 5 || countThree == 5) //SO you know you got 5 consecutively
            {
                return true;
            }
            else
            {
                count = 0;
                countTwo = 0;
                
            }
            if (bingo[i][i] == 0)
            {
                countThree++; //check for diagonal from bottom left to right top
            }
            if (bingo[i][4-i] == 0)
            {
                countFour++; //check for diagonal from bottom right to left top
            }
            if (countThree == 5 || countFour == 5)
            {
                return true; //let the main method know the user won
            } 
        }
        return false;
    }
    
    public static void makeRow(int x [][], int start, int end, int num, ArrayList<Integer> a)
    {
        for (int i = 0; i < 5; i++)
        {
            Random rand = new Random();
            int  n = rand.nextInt(end) + start; //This is the random range that the row is in 
            if (a.indexOf(n) >= 0)
            {
                x[i][num] = n;
                a.remove(a.indexOf(n)); //remove from the arraylist once the number has been used
            }
            else
            {
                i--; //go back and get another number if the number was repeated 
            }
        }  
   }
    /*       
    public static void makeBRow(int x [][], ArrayList<Integer> B){
        for (int i = 0; i < 5; i++)
        {
            Random rand = new Random();
            int  n = rand.nextInt(15) + 1;
            if (B.indexOf(n) >= 0)
            {
                x[i][0] = n;
                B.remove(B.indexOf(n));
            }
            else
            {
                i--;
            }
        }  
    }
    public static void makeIRow(int x [][], ArrayList<Integer> I){
        for (int i = 0; i < 5; i++)
        {
            Random rand = new Random();
            int  n = rand.nextInt(30) + 16;
            if (I.indexOf(n) >= 0)
            {
                x[i][1] = n;
                I.remove(I.indexOf(n));
            }
            else
            {
                i--;
            }
        }  
    }
    public static void makeNRow(int x [][], ArrayList<Integer> N){
        for (int i = 0; i < 5; i++)
        {
            Random rand = new Random();
            int  n = rand.nextInt(45) + 31;
            if (N.indexOf(n) >= 0)
            {
                x[i][2] = n;
                N.remove(N.indexOf(n));
            }
            else
            {
                i--;
            }
        }   
    }
    public static void makeGRow(int x [][], ArrayList<Integer> G){
        for (int i = 0; i < 5; i++)
        {
            Random rand = new Random();
            int  n = rand.nextInt(60) + 46;
            if (G.indexOf(n) >= 0)
            {
                x[i][3] = n;
                G.remove(G.indexOf(n));
            }
            else
            {
                i--;
            }
        }  
    }
    public static void makeORow(int x [][], ArrayList<Integer> O){
        for (int i = 0; i < 5; i++)
        {
            Random rand = new Random();
            int  n = rand.nextInt(75) + 61;
            if (O.indexOf(n) >= 0)
            {
                x[i][4] = n;
                O.remove(O.indexOf(n));
            }
            else
            {
                i--;
            }
        }  
    }
    */
}