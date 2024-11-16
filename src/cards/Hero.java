package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Hero extends Card {
    private static final int INITIAL_HEALTH = 30;
    public Hero(final CardInput cardInput) {
        super(cardInput);
        this.setHealth(INITIAL_HEALTH);
    }

    /**
     * Method to output the hero's attributes to a JSON object
     * @param hero
     * @param outputNode
     */
    public static void outputHero(final Hero hero, final ObjectNode outputNode) {
        outputNode.put("mana", hero.getMana());
        outputNode.put("description", hero.getDescription());
        ArrayNode colors = outputNode.putArray("colors");
        for (String color : hero.getColors()) {
            colors.add(color);
        }
        outputNode.put("name", hero.getName());
        outputNode.put("health", hero.getHealth());
    }
}
