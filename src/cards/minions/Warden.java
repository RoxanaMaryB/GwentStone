package cards.minions;

import fileio.*;
import cards.*;
public class Warden extends Minion{
    public Warden(CardInput cardInput){
        super(cardInput);
        this.row = 0;
        this.isTank = true;
    }
}
