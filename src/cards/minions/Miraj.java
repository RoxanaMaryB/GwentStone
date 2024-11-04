package cards.minions;

import fileio.*;
import cards.*;
public class Miraj extends Minion{
    public Miraj(CardInput cardInput){
        super(cardInput);
        this.isSpecial = true;
        this.row = 0;
    }
}
