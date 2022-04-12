package pl.edu.agh.kis.pz1;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertTrue;

/**
 * Class testing Server Class
 */
public class ServerTest {
    Server server;
    ServerSocket serverSocket;
    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(Server.PORT);
        server = new Server(serverSocket);
    }
    @Test
    public void closeServerSocket() throws IOException {
        assertTrue(server.closeServerSocket());
    }
}