package cards;

import lombok.Getter;
import lombok.Setter;
import fileio.*;

import java.util.ArrayList;

/**
 * Takes an array of CardInput and changes it into an array of Card object
 */
@Getter @Setter
public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    public Deck(ArrayList<CardInput> deckInput) {
        for (CardInput cardInput : deckInput) {
            cards.add(new Card(cardInput));
        }
    }
}
