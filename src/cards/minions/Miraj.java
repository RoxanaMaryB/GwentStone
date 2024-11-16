package cards.minions;

import fileio.CardInput;
import cards.Minion;
public class Miraj extends Minion {
    public Miraj(final CardInput cardInput) {
        super(cardInput);
        this.isSpecial = true;
        this.row = 0;
    }
}
