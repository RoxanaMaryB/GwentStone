package cards;

import fileio.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Card {
    protected CardInput cardInput;

    protected boolean hasAttacked = false;

    public Card(CardInput cardInput) {
        this.cardInput = cardInput;
    }
}
