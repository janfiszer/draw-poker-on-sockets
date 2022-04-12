package pl.edu.agh.kis.pz1.exeptions;

public class DeckOutOfCardsException extends Exception{
    public DeckOutOfCardsException(String message){
        super(message);
    }
}
