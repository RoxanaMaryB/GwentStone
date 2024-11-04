package cards;

import fileio.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Card {
    private CardInput cardInput;

    protected boolean hasAttacked = false;

    public Card(CardInput cardInput) {
        this.cardInput = cardInput;
    }
}
