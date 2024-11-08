package cards;

import lombok.Getter;
import lombok.Setter;
import fileio.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import cards.minions.*;

/**
 * Takes an array of CardInput and changes it into an array of Card object
 */
@Getter @Setter
public class Deck {
    private ArrayList<Minion> minions = new ArrayList<>();
    public Deck(ArrayList<CardInput> deckInput) {
        for (CardInput cardInput : deckInput) {
            Card card = CardFactory.createCard(cardInput);
            minions.add((Minion) card);
        }
    }

    public static void outputDeck(Deck deck, ArrayNode outputNode) {
        for (Minion minion : deck.getMinions()) {
            ObjectNode cardNode = outputNode.addObject();
            Minion.outputMinion(minion,cardNode);
        }
    }
}
