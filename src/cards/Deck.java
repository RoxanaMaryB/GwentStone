package cards;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import java.util.ArrayList;

/**
 * Takes an array of CardInput and changes it into an array of Card object
 */
@Getter @Setter
public class Deck {
    private ArrayList<Minion> minions = new ArrayList<>();
    public Deck(final ArrayList<CardInput> deckInput) {
        for (CardInput cardInput : deckInput) {
            Card card = CardFactory.createCard(cardInput);
            minions.add((Minion) card);
        }
    }

    /**
     * Outputs the names of the cards in deck to the output node
     * @param deck the deck
     * @param outputNode the output node
     */
    public static void outputDeck(final Deck deck, final ArrayNode outputNode) {
        for (Minion minion : deck.getMinions()) {
            ObjectNode cardNode = outputNode.addObject();
            Minion.outputMinion(minion, cardNode);
        }
    }
}
