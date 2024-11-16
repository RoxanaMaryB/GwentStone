package cards;

import fileio.CardInput;
import cards.minions.Sentinel;
import cards.minions.Berserker;
import cards.minions.Goliath;
import cards.minions.Warden;
import cards.minions.TheRipper;
import cards.minions.Miraj;
import cards.minions.TheCursedOne;
import cards.minions.Disciple;
import cards.heroes.LordRoyce;
import cards.heroes.EmpressThorina;
import cards.heroes.KingMudface;
import cards.heroes.GeneralKocioraw;

/**
 * Factory class for creating card objects
 */
public final class CardFactory {

    private CardFactory() { }

    /**
     * Creates a card object based on the card name
     * @param cardInput the card input
     * @return the card object
     */
    public static Card createCard(final CardInput cardInput) {
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
