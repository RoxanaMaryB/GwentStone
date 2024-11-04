package cards.minions;

import fileio.*;
import cards.*;
public class TheCursedOne extends Minion{
    public TheCursedOne(CardInput cardInput){
        super(cardInput);
        this.isSpecial = true;
        this.row = 1;
    }
}
