package cards;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public static void outputMinion(Minion minion, ObjectNode outputNode) {
        outputNode.put("mana", minion.getCardInput().getMana());
        outputNode.put("attackDamage", minion.getCardInput().getAttackDamage());
        outputNode.put("health", minion.getCardInput().getHealth());
        outputNode.put("description", minion.getCardInput().getDescription());
        ArrayNode colors = outputNode.putArray("colors");
        for (String color : minion.getCardInput().getColors()) {
            colors.add(color);
        }
        outputNode.put("name", minion.getCardInput().getName());
    }
}