package cards;

import java.util.HashMap;
import java.util.Map;

import fileio.*;
import cards.minions.*;
import cards.heroes.*;

public class CardFactory {
    private static Map<String, Class<? extends Card>> cardMap = new HashMap<>();

    static {
        cardMap.put("Sentinel", Sentinel.class);
        cardMap.put("Berserker", Berserker.class);
        cardMap.put("Goliath", Goliath.class);
        cardMap.put("Warden", Warden.class);
        cardMap.put("The Ripper", TheRipper.class);
        cardMap.put("Miraj", Miraj.class);
        cardMap.put("The Cursed One", TheCursedOne.class);
        cardMap.put("Disciple", Disciple.class);
        cardMap.put("Lord Royce", LordRoyce.class);
        cardMap.put("Empress Thorina", EmpressThorina.class);
        cardMap.put("King Mudface", KingMudface.class);
        cardMap.put("General Kocioraw", GeneralKocioraw.class);
    }

    public static Card createCard(CardInput cardInput) {
        Class<? extends Card> cardName = cardMap.get(cardInput.getName());
        try {
            return cardName.getConstructor(CardInput.class).newInstance(cardInput);
        } catch (Exception e) {
            return null;
        }
    }
}
