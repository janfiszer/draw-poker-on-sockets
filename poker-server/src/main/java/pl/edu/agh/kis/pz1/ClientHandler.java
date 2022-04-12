package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class to handle client by a server and communicate with it
 *
 */
public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    //string message
    private String clientUsername;
    Player player;

    public String getClientUsername() {
        return clientUsername;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    /**
     * ClientHandler constructor- takes socket to connect with client and sets all attributes
     * @param socket socket to connect to socket
     */
    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            player = new Player(clientUsername);
            broadcastMessage("SERWER: " + clientUsername + " has joined the game");
        }
        catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Closes socket, bufferReader and bufferWriter
     * @param socket socket to close
     * @param bufferedReader buffer to close
     * @param bufferedWriter buffer to close
     * @return true when succeed and false when not
     */
    public boolean closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
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
     * Sends message to all clients except *this*
     * @param message message to send
     */
    public void broadcastMessage(String message){
        for (ClientHandler clientHandler: clientHandlers){
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    /**
     * Sends message to all clients
     * @param message message to send
     */
    public void toEveryone(String message){
        for (ClientHandler clientHandler: clientHandlers){
            try {
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    /**
     * Sends message to *this* client
     * @param message message to send
     */
    public void sendMessageToThisClient (String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Removes clientHandler object from clientHandlers list
     */
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER:" + clientUsername + "has left the game");
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(clientUsername + ": " + messageFromClient);
            }
            catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
}
