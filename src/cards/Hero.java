package cards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

public class Hero extends Card{
    public Hero (CardInput cardInput) {
        super(cardInput);
        cardInput.setHealth(30);
    }

    public static void outputHero(Hero hero, ObjectNode outputNode) {
        outputNode.put("mana", hero.getCardInput().getMana());
        outputNode.put("description", hero.getCardInput().getDescription());
        ArrayNode colors = outputNode.putArray("colors");
        for (String color : hero.getCardInput().getColors()) {
            colors.add(color);
        }
        outputNode.put("name", hero.getCardInput().getName());
        outputNode.put("health", hero.getCardInput().getHealth());
    }
}
