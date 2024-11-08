package cards;

import fileio.*;
import cards.minions.*;
import cards.heroes.*;

public class CardFactory {

    public static Card createCard(CardInput cardInput) {
        String cardName = cardInput.getName();
        switch (cardName) {
            case "Sentinel":
                return new Sentinel(cardInput);
            case "Berserker":
                return new Berserker(cardInput);
            case "Goliath":
                return new Goliath(cardInput);
            case "Warden":
                return new Warden(cardInput);
            case "The Ripper":
                return new TheRipper(cardInput);
            case "Miraj":
                return new Miraj(cardInput);
            case "The Cursed One":
                return new TheCursedOne(cardInput);
            case "Disciple":
                return new Disciple(cardInput);
            case "Lord Royce":
                return new LordRoyce(cardInput);
            case "Empress Thorina":
                return new EmpressThorina(cardInput);
            case "King Mudface":
                return new KingMudface(cardInput);
            case "General Kocioraw":
                return new GeneralKocioraw(cardInput);
            default:
                return null;
        }
    }
}