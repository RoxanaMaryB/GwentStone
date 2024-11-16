package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
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

    public Minion(final CardInput cardInput) {
        super(cardInput);
        this.setAttackDamage(cardInput.getAttackDamage());
    }

    /**
     * Method to output the minion's attributes to a JSON object
     * @param minion Minion to output
     * @param outputNode JSON object to output to
     */
    public static void outputMinion(final Minion minion, final ObjectNode outputNode) {
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
