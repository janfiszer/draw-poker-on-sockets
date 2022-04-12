package pl.edu.agh.kis.pz1;

import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.logging.SocketHandler;

import static org.junit.Assert.*;

/**
 * Class testing Client class
 */
public class ClientTest {
    Client client;
    Socket socket;
    @Before
    public void setUp() throws Exception {
        socket = new Socket();
        client = new Client(socket, "jonson19");

    }

    @Test
    public void closeEverything() {
        assertTrue(client.closeEverything(socket, client.getBufferedReader(), client.getBufferedWriter()));
    }
}