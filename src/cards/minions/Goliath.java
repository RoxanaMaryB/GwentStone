package cards.minions;

import fileio.CardInput;
import cards.Minion;
public class Goliath extends Minion {
    public Goliath(final CardInput cardInput) {
        super(cardInput);
        this.row = 0;
        this.isTank = true;
    }
}
