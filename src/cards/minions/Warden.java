package cards.minions;

import fileio.CardInput;
import cards.Minion;
public class Warden extends Minion {
    public Warden(final CardInput cardInput) {
        super(cardInput);
        this.row = 0;
        this.isTank = true;
    }
}
