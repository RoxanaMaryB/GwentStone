package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

public class Hero extends Card{
    public Hero (CardInput cardInput) {
        super(cardInput);
        this.setHealth(30);
    }

    public static void outputHero(Hero hero, ObjectNode outputNode) {
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
