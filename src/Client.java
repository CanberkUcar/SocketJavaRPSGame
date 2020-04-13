/* 17290124 Canberk Uçar CompEng %100 Eng */

/* Imports ------------ */
import java.net.*;
import java.util.Scanner;
import java.io.*;

/** CLIENT CLASS */
public class Client {
    public static void main(String[] args) throws IOException{

        // Socket to subscribe into. -----------------
        Socket socket = new Socket("localhost", 3999);

        /**
         * SECTION Data Connection Testing --------------------------------- 
         * Two way communication testing. Sending Test Data from: Client
         */
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("Testing data communication..."); // Test String
        printWriter.flush(); // Closing PrintWriter ------------------------

        /**
         * SECTION --------------------------------------------------------------------------
         * Receive Data Connection Testing: receivedData (string)
         */
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receivedData = bufferedReader.readLine();
        System.out.println("Server: " + receivedData); // Printing. -------------------------

        /** 
         * SECTION User Role ----------------------------------------
         * Gets the statement of rock / paper / scissor from the user.
         * Move: Enum type for statement.
         * sendData: Method for Sending data from Client to Server.
         */

        printUserOptions(); // Terminal Printing.

        // Getting option from user.
        Scanner scanner = new Scanner(System.in);
        String userEntry = scanner.nextLine();
        
        // Sending User Entry to Server.
        sendData(userEntry, printWriter);

        // Getting the result from Server.
        receiveData(userEntry, bufferedReader); 
        // END OF MAIN METHOD ---------------------------------------------------------------
    }

    /** ANCHOR Print Method for User -----------------------
     * Usual Print Method for terminal usage.
     * Getting User Options to the terminal.
     */
    static void printUserOptions(){
        System.out.println("-----------------------------");
        System.out.println("WAITING FOR PLAYER TO TYPE...");
        System.out.println("      Rock: is for Rock      ");
        System.out.println("     Paper: is for Paper     ");
        System.out.println("   Scissor: is for Scissor   ");
        System.out.println("-----------------------------");
    }

    /** ANCHOR Send Method for Client -------------------------------
     * Sends the data to Server using Socket.
     */
    static void sendData(String userEntry, PrintWriter printWriter){
        printWriter.println(userEntry); // User Entry Data.
        printWriter.flush(); // Closing PrintWriter -----------------
    }

    /** ANCHOR Receive Method for Client ----------------------------------------
     * Reads the data from the socket and prints content to screen.
     */
    static void receiveData(String receivedData, BufferedReader bufferedReader){
        try {
            receivedData = bufferedReader.readLine();
            System.out.println("Server: " + receivedData); // Printing. 
        } catch(Exception ex){ System.out.println(ex); } // Ex Handling ---------
    }
}