package pl.edu.agh.kis.pz1;

import org.apache.commons.lang3.ObjectUtils;
import pl.edu.agh.kis.pz1.util.Player;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents client, includes main to run
 */
public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;
    private static Logger logger = null;

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    static
    {
        System.setProperty(
                "java.util.logging.SimpleFormatter.format",
                "%5$s %n");
        logger = Logger.getLogger(
                Client
                        .class.getName());
    }
    /**
     * Creates client object on given socket, set username
     * @param socket socket to connect
     * @param username username to set
     */
    public Client(Socket socket, String username){
        try{
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Close socket, bufferWriter, bufferReader
     * @param socket to shut down
     * @param bufferedReader to shut down
     * @param bufferedWriter to shut down
     */
    public boolean closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (socket != null){
                socket.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Send username to Server(ClientHandler) using buffer
     */
    public void sendUsername() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Send message from system input to Server(ClientHandler) using buffer
     */
    public void sendMessage() {
        try {
            sendUsername();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Creates thread and listen for messages
     */
    public void listenForMessage(){
        new Thread(() -> {
            String messageFromPlayers;

            while(socket.isConnected()){
                try{
                    messageFromPlayers = bufferedReader.readLine();
                    logger.log(Level.INFO, messageFromPlayers);
                }
                catch (IOException e){
                    closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        logger.log(Level.INFO, "Enter your nick for the game");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", Server.PORT);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
