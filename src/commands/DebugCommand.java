package commands;

import cards.Deck;
import cards.Hero;
import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.logic.Coordinates;
import game.logic.GameTable;
import game.logic.GameUtils;

public class DebugCommand extends Command {

    public DebugCommand(final ActionsInput action, final GameTable table, final ArrayNode output) {
        super(action, table, output);
    }

    /**
     * Outputs the deck of the player with the given index
     * @param playerIdx
     */
    public static void getPlayerDeck(final int playerIdx) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        newNode.put("playerIdx", context.getAction().getPlayerIdx());
        if (context.getAction().getPlayerIdx() == 1) {
            ArrayNode outputArray = newNode.putArray("output");
            Deck.outputDeck(context.getTable().getPlayerOneDeck(), outputArray);
        } else if (context.getAction().getPlayerIdx() == 2) {
            ArrayNode outputArray = newNode.putArray("output");
            Deck.outputDeck(context.getTable().getPlayerTwoDeck(), outputArray);
        } else {
            newNode.put("output", "Invalid player index");
        }
    }

    /**
     * Outputs the cards in the hand of the player with the given index
     * @param playerIdx
     */
    public static void getCardsInHand(final int playerIdx) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        newNode.put("playerIdx", context.getAction().getPlayerIdx());
        if (context.getAction().getPlayerIdx() == 1) {
            ArrayNode outputArray = newNode.putArray("output");
            GameTable.outputHand(context.getTable().getPlayerOneHand(), outputArray);
        } else if (context.getAction().getPlayerIdx() == 2) {
            ArrayNode outputArray = newNode.putArray("output");
            GameTable.outputHand(context.getTable().getPlayerTwoHand(), outputArray);
        } else {
            newNode.put("output", "Invalid player index");
        }
    }

    /**
     * Outputs all the cards on the table
     */
    public static void getCardsOnTable() {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        ArrayNode outputArray = newNode.putArray("output");
        GameTable.outputTable(context.getTable().getTable(), outputArray);
    }

    /**
     * Outputs the player that has the turn
     */
    public static void getPlayerTurn() {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        newNode.put("output", GameUtils.getCurrentPlayer());
    }

    /**
     * Outputs the hero of the player with the given index
     * @param playerIdx
     */
    public static void getPlayerHero(final int playerIdx) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        newNode.put("playerIdx", context.getAction().getPlayerIdx());
        if (context.getAction().getPlayerIdx() == 1) {
            ObjectNode outputObject = newNode.putObject("output");
            Hero.outputHero(context.getTable().getPlayerOneHero(), outputObject);
        } else if (context.getAction().getPlayerIdx() == 2) {
            ObjectNode outputObject = newNode.putObject("output");
            Hero.outputHero(context.getTable().getPlayerTwoHero(), outputObject);
        } else {
            newNode.put("output", "Invalid player index");
        }
    }

    /**
     * Outputs the card at the given position
     * @param coordinates
     */
    public static void getCardAtPosition(final Coordinates coordinates) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        int x = coordinates.getX();
        int y = coordinates.getY();
        newNode.put("x", x);
        newNode.put("y", y);
        if (x < 0 || x >= context.getTable().getTable().size() || y < 0
                || y >= context.getTable().getTable().get(x).size()) {
            newNode.put("output", "No card available at that position.");
        } else {
            ObjectNode outputObject = newNode.putObject("output");
            Minion.outputMinion(context.getTable().getTable().get(x).get(y), outputObject);
        }
    }

    /**
     * Outputs the mana of the player with the given index
     * @param playerIdx
     */
    public static void getPlayerMana(final int playerIdx) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode = context.getOutput().addObject();
        newNode.put("command", context.getAction().getCommand());
        newNode.put("playerIdx", context.getAction().getPlayerIdx());
        if (context.getAction().getPlayerIdx() == 1) {
            newNode.put("output", GameUtils.getPlayerOneMana());
        } else if (context.getAction().getPlayerIdx() == 2) {
            newNode.put("output", GameUtils.getPlayerTwoMana());
        } else {
            newNode.put("output", "Invalid player index");
        }
    }
}
