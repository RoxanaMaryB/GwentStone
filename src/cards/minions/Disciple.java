package cards.minions;

import fileio.CardInput;
import cards.Minion;
public class Disciple extends Minion {
    public Disciple(final CardInput cardInput) {
        super(cardInput);
        this.isSpecial = true;
        this.row = 1;
    }
}
