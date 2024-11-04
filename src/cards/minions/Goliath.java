package cards.minions;

import fileio.*;
import cards.*;
public class Goliath extends Minion{
    public Goliath(CardInput cardInput){
        super(cardInput);
        this.row = 0;
        this.isTank = true;
    }
}
