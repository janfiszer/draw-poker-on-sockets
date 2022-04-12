package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.exeptions.DeckOutOfCardsException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing server, uses a lot of class *Game*
 */
public class Server {
    public static final int PORT = 2115;
    private ServerSocket serverSocket;
    static Game game;
    private final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Creates server on given socket
     * @param serverSocket socket on which is creates
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Logger getLogger() {
        return logger;
    }

    /**
     * Start listening for players and if their amount reaches the given quantity it ends that
     * @param players_quantity number of required players to start game
     * @throws IOException InOutException
     */
    public void startServer(int players_quantity) throws IOException {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                logger.log(Level.INFO, "A new player has connected!\n");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

                if (ClientHandler.clientHandlers.size() == players_quantity) {
                    clientHandler.toEveryone("Reached required amount of players (" + players_quantity + ")," +
                            " game is about to start\n");
                    break;
                }
            }
        }
        catch (IOException e) {
            closeServerSocket();
        }
    }

    /**
     * Closes serveSocket
     * @throws IOException if thrown printsStackTrace
     */
    public boolean closeServerSocket() throws IOException {
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws IOException, DeckOutOfCardsException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        Server server = new Server(serverSocket);
        Logger logger = server.getLogger();

        boolean rematch = true;
        logger.log(Level.INFO , "How many players game such include?");
        Scanner scanner = new Scanner(System.in);
        int pQ = scanner.nextInt();
        server.startServer(pQ);

        while(rematch) {
            game = new Game();

            TimeUnit.SECONDS.sleep(2);

            logger.log(Level.INFO, "GAME STARTED\n\n");

            game.playersDrawCards();
            game.showPlayersHands();

            logger.log(Level.INFO, "PLAYERS EXCHANGING THEIR CARDS\n\n");
            game.playersExchangeCards();

            game.checkWhoWins();
            logger.log(Level.INFO, "GAME ENDED\nPLAYERS CHOOSING WHETHER TO PLAY AGAIN\n\n");

            rematch = game.rematch();
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
