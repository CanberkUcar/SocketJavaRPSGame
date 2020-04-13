/* 17290124 Canberk Uçar CompEng %100 Eng */

/* Imports ------------ */
import java.net.*;
import java.util.Random;
import java.io.*;

/* SERVER CLASS */
public class Server {
    
    // Attributes -----------------------------
    private static Move userEntryMove = null;
    private static Move serverEntryMove = null;
    private static int userWin = 0;
    private static int serverWin = 0;
    private static int tieSituation = 0;

    public static void main(String[] args) throws IOException{
        
        // Creating ServerSocket and Accepting from Client.
        ServerSocket serverSocket = new ServerSocket(3999);
        Socket socket = serverSocket.accept();
        System.out.println("Client has connected...");

        /** SECTION Data Connection Testing --------------------------------------------------
         * Two way communication testing.
         * Receiving Test: receivedData (string), from: Client
         */
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receivedData = bufferedReader.readLine();
        System.out.println("Client: " + receivedData); // Printing.

        // Send Data Connection Testing Data from: Server. -----------------
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("Data Testing OK."); // Test String
        printWriter.flush(); // Closing PrintWriter ------------------------------------------

        // STEPS -----------------------------------------------------------------------------
        receiveData(receivedData, bufferedReader); // REVIEW READS USER ENTRY.

        generateRandomMove(); // REVIEW SERVER MAKES MOVE.

        compareMoves(); // REVIEW COMPARING MOVES.

        resultWhoWon(printWriter); // REVIEW GETTING THE RESULT AND SENDING TO CLIENT.
        // END OF MAIN METHOD ----------------------------------------------------------------
    }

    /** ANCHOR Receive Method for Server -------------------------
     * Reads the data from the socket and prints content to screen
     * Also converts User Move. receivedData(string) to Move.
     * Ref: convertUserMove();
     */
    static void receiveData(String receivedData, BufferedReader bufferedReader){
        try {
            receivedData = bufferedReader.readLine();
            System.out.println("Client: " + receivedData); // Printing. 
        }catch(Exception ex){ System.out.println(ex); }
        
        // STUB Converting Move.
        convertUserMove(receivedData);

    }

    /** ANCHOR Random Move -------------------------------------
     * Generates a random variable and prints content to screen.
     * Variable of course converts to a Move(Enum)
     * Ref: randomMove()
     */
    static void generateRandomMove(){
        Random random = new Random();
        int randomValue = (random.nextInt(3)*7)%3;
        // STUB Get Move from Value. ---------------
        serverEntryMove = randomMove(randomValue+1);
        System.out.println(serverEntryMove);
    }

    /** Selecting a RandomValue to a Move
     * 1 is for Rock --------------------
     * 2 is for Paper -------------------
     * 3 is for Scissor -----------------
     */
    static Move randomMove(int randomValue){
        if(randomValue == 1) { return Move.Rock; }
        if(randomValue == 2) { return Move.Paper; }
        if(randomValue == 3) { return Move.Scissor; }
        else { return null; }
    }

    /** ANCHOR User Move -----------------------------
     * Converts User's Move into a Enum.
     * Case Non-Sensitive with toLowerCase()
     */
    static Move convertUserMove(String userEntry){
        if (userEntry.toLowerCase().equals("rock")) { 
            userEntryMove = Move.Rock;
            System.out.println("Rock Selected.");   }
        else if (userEntry.toLowerCase().equals("paper")){
            userEntryMove = Move.Paper;
            System.out.println("Paper Selected.");  }
        else if (userEntry.toLowerCase().equals("scissor")){
            userEntryMove = Move.Scissor;
            System.out.println("Scissor Selected."); }
        return userEntryMove;   
    }

    /** ANCHOR Compare Moves ----------------------------------------------
     * Game Logic defined here.
     */
    static void compareMoves(){
        if(userEntryMove == Move.Rock){
            if(serverEntryMove == Move.Rock){ tieSituation++; }
            else if(serverEntryMove == Move.Paper){ serverWin++; }
            else if(serverEntryMove == Move.Scissor) { userWin++; }
        }
        else if(userEntryMove == Move.Paper){
            if(serverEntryMove == Move.Rock){ userWin++; }
            else if(serverEntryMove == Move.Paper){ tieSituation++; }
            else if(serverEntryMove == Move.Scissor) { serverWin++; }
        }
        else if(userEntryMove == Move.Scissor){
            if(serverEntryMove == Move.Rock){ serverWin++; }
            else if(serverEntryMove == Move.Paper){ userWin++; }
            else if(serverEntryMove == Move.Scissor) { tieSituation++; }
        }
    }

    /** ANCHOR Result of Game -------------------------
     * Getting result and sending it through socket.
     */
    static void resultWhoWon(PrintWriter printWriter){
        if(serverWin == 1) { 
            String resultString = "Server has Won!"; 
            // Send the info to Client. STUB
            sendData(resultString, printWriter);
        }
        else if(userWin == 1){
            String resultString = "User has Won!";
            // Send the info to Client. STUB
            sendData(resultString, printWriter);
        }
        else if(tieSituation == 1){
            String resultString = "Tie Situation!";
            // Send the info to Client. STUB
            sendData(resultString, printWriter);
        }
    }

    /** ANCHOR Send Method for Server ----------------------------------
     * Sends the data to client using Socket.
     */
    static void sendData(String resultString, PrintWriter printWriter){
        printWriter.println(resultString); // Result Entry Data.
        printWriter.flush(); // Closing PrintWriter --------------------
    }
}