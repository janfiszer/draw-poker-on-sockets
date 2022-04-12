package pl.edu.agh.kis.pz1;

import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ClientHandlerTest {
    ClientHandler clientHandler;
    Socket socket;

    @Test
    public void closeEverything() {
        socket = new Socket();
        clientHandler = new ClientHandler(socket);
        assertTrue(clientHandler.closeEverything(socket, clientHandler.getBufferedReader(), clientHandler.getBufferedWriter()));
    }
}