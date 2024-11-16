package cards.minions;

import fileio.CardInput;
import cards.Minion;
public class TheRipper extends Minion {
    public TheRipper(final CardInput cardInput) {
        super(cardInput);
        this.isSpecial = true;
        this.row = 0;
    }
    // to check if this.attack is < 2
}
