package cards;

import java.util.ArrayList;
import fileio.*;

/**
 * Minion class is the base class for all minions in the game
 * Extends Card class
 * Row = 0 => front row
 * Row = 1 => back row
 */
public class Minion extends Card {

    protected int attack = 0;
    protected boolean isSpecial = false;
    protected boolean isFrozen = false;
    protected boolean isTank = false;
    protected int row = 0;

    public Minion(CardInput cardInput) {
        super(cardInput);
        this.attack = cardInput.getAttackDamage();
    }

    public void defrost() {
        isFrozen = false;
    }
}
