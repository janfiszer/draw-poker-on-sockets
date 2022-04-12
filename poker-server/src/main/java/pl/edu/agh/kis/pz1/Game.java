package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.exeptions.DeckOutOfCardsException;
import pl.edu.agh.kis.pz1.util.Card;
import pl.edu.agh.kis.pz1.util.Deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing game of poker used by server
 * Has a *Deck* and methods to communicate with clients bo clientHandler
 */
public class Game {
    Deck deck;
    int pool = 0;
    /**
     * Creats a deck and shuffles it
     */
    public Game(){
        deck = new Deck();
        deck.shuffle();
    }

    /**
     * Gives each player(client) 5 cards
     * @throws DeckOutOfCardsException when deck runs out of cards
     */
    public void playersDrawCards() throws DeckOutOfCardsException {
        for (ClientHandler clientHandler: ClientHandler.clientHandlers){
            clientHandler.player.startingDraw(deck);
        }
    }

    /**
     * Shows hand to a client using loggers
     */
    public void showPlayersHands(){
        for (ClientHandler clientHandler : ClientHandler.clientHandlers){
            clientHandler.sendMessageToThisClient("\nThose are your cards:");
            printHand(clientHandler);
        }
    }

    /**
     * helper function used by showPlayersHand() to show cards with indexes
     * @param clientHandler to which clientHandler show hand
     */
    private void printHand(ClientHandler clientHandler){
        int i = 1;
        for (Card card: clientHandler.player.getHand()){
            String toSend = i + ". " + card.toString();
            clientHandler.sendMessageToThisClient(toSend);
            i++;
        }
    }

    /**
     * Takes a string form buffer and changes it to array of integers
     * Used bo playersExchangeCards()
     * @param clientHandler function need to know which player wants to change which cards
     * @return list of integers representing indexes of cards the player want to change
     * @throws IOException when scanner mesh something up
     */
    private ArrayList<Integer> arrToChange(ClientHandler clientHandler) throws IOException {
        ArrayList<Integer> ret = new ArrayList<>();
        String indexes = clientHandler.getBufferedReader().readLine();

        try (Scanner scanner = new Scanner(indexes)) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int index = scanner.nextInt() - 1;
                    if (index > 4 || index < 0) {
                        throw new IndexOutOfBoundsException();
                    } else {
                        if (!ret.contains(index))
                            ret.add(index);
                    }
                } else {
                    scanner.next();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            clientHandler.sendMessageToThisClient("Wrong indexes");
        }
        return ret;
    }

    /**
     * Let's all players to change their cards
     * @throws IOException when scanner mesh something up (in arrToChange(ClientHandler clientHandler))
     * @throws DeckOutOfCardsException when deck run out of cards
     */
    public void playersExchangeCards() throws IOException, DeckOutOfCardsException {
        for (ClientHandler clientHandler: ClientHandler.clientHandlers){
            clientHandler.broadcastMessage("\n\nWait for your turn");
            clientHandler.sendMessageToThisClient("""


                    Say something to other players and then,
                    select INDEXES of card you want to change, all in one line""");
            clientHandler.player.changeHand(arrToChange(clientHandler), deck);
            clientHandler.sendMessageToThisClient("Your cards after exchange:");
            printHand(clientHandler);
        }
    }

    /**
     * Check which player has the strongest hand ranking and gives the information to players about it
     */
    public void checkWhoWins(){
        // First player set as a winner
        ClientHandler winner = ClientHandler.clientHandlers.get(0);
        for (ClientHandler clientHandler: ClientHandler.clientHandlers){
            clientHandler.player.checkHand();
            if (winner.player.best_hand_ranking.compareTo(clientHandler.player.best_hand_ranking) < 0){
                winner = clientHandler;
            }
            else if (winner.player.best_hand_ranking.compareTo(clientHandler.player.best_hand_ranking) == 0){
                Card [] hand = clientHandler.player.getHand();
                Card [] winner_hand = winner.player.getHand();
                if (hand[clientHandler.player.index_of_best].compareTo(winner_hand[winner.player.index_of_best]) > 0){
                    winner = clientHandler;
                }
            }
        }
        winner.sendMessageToThisClient("\n\nCongrats, you won!\n" +
                "Your best hand ranking is: " + winner.player.best_hand_ranking + "\n");
        winner.broadcastMessage("\n\n" + winner.getClientUsername() + " have won!\n" +
                "His best ranking is: " + winner.player.best_hand_ranking + "\n");
    }


    /**
     * Ask whether clients what to play rematch
     * @return true when all clients agree to play rematch, false otherwise
     * @throws IOException when buffer messes up
     */
    public boolean rematch() throws IOException {
        ClientHandler.clientHandlers.get(0).toEveryone("Again say something to others,\n" +
                "Want to play again? (y/n)");
        for (ClientHandler clientHandler: ClientHandler.clientHandlers){
            String answer = clientHandler.getBufferedReader().readLine();
            if (!answer.equals("y"))
                return false;
            else {
                clientHandler.sendMessageToThisClient("You have agreed! Waiting for others...");
            }
        }
        ClientHandler.clientHandlers.get(0).toEveryone("Everyone agreed game is about to start");
        return true;
    }

}
