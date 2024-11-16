package cards.minions;

import fileio.CardInput;
import cards.Minion;
public class TheCursedOne extends Minion {
    public TheCursedOne(final CardInput cardInput) {
        super(cardInput);
        this.isSpecial = true;
        this.row = 1;
    }
}
