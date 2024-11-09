package cards;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Minion class is the base class for all minions in the game
 * Extends Card class
 * Row = 0 => front row
 * Row = 1 => back row
 */
@Getter @Setter
public class Minion extends Card {

    protected boolean isSpecial = false;
    protected boolean isTank = false;
    private boolean isFrozen = false;
    protected int row;

    public Minion(CardInput cardInput) {
        super(cardInput);
        this.setAttackDamage(cardInput.getAttackDamage());
    }

    public static void outputMinion(Minion minion, ObjectNode outputNode) {
        outputNode.put("mana", minion.getMana());
        outputNode.put("attackDamage", minion.getAttackDamage());
        outputNode.put("health", minion.getHealth());
        outputNode.put("description", minion.getDescription());
        ArrayNode colors = outputNode.putArray("colors");
        for (String color : minion.getColors()) {
            colors.add(color);
        }
        outputNode.put("name", minion.getName());
    }
}
