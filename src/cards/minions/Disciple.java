package cards.minions;

import fileio.*;
import cards.*;
public class Disciple extends Minion{
    public Disciple(CardInput cardInput){
        super(cardInput);
        this.isSpecial = true;
        this.row = 1;
    }
}
