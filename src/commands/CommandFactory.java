package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cards.*;
import fileio.ActionsInput;
import game.logic.GameTable;
import game.logic.GameUtils;

public class CommandFactory {
    public void executeCommand(Command command) {
        ActionsInput action = command.getAction();
        GameTable table = command.getTable();
        ArrayNode output = command.getOutput(); // the output from big main
        ObjectNode newNode = output.addObject();
        newNode.put("command", action.getCommand());
        switch(action.getCommand()) {
            case "getPlayerDeck":
                newNode.put("playerIdx", action.getPlayerIdx());
                if(action.getPlayerIdx() == 1) {
                    ArrayNode outputArray = newNode.putArray("output");
                    Deck.outputDeck(table.getPlayerOneDeck(), outputArray);
                } else if(action.getPlayerIdx() == 2) {
                    ArrayNode outputArray = newNode.putArray("output");
                    Deck.outputDeck(table.getPlayerTwoDeck(), outputArray);
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "getCardsInHand":
                newNode.put("playerIdx", action.getPlayerIdx());
                if(action.getPlayerIdx() == 1) {
                    ArrayNode outputArray = newNode.putArray("output");
                    GameTable.outputHand(table.getPlayerOneHand(), outputArray);
                } else if(action.getPlayerIdx() == 2) {
                    ArrayNode outputArray = newNode.putArray("output");
                    GameTable.outputHand(table.getPlayerTwoHand(), outputArray);
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "getCardsOnTable":
                ArrayNode outputArray = newNode.putArray("output");
                GameTable.outputTable(table.getTable(), outputArray);
                break;
            case "getPlayerTurn":
                newNode.put("output", GameUtils.getCurrentPlayer());
                break;
            case "getPlayerHero":
                newNode.put("playerIdx", action.getPlayerIdx());
                if(action.getPlayerIdx() == 1) {
                    ObjectNode outputObject = newNode.putObject("output");
                    Hero.outputHero(table.getPlayerOneHero(), outputObject);
                } else if(action.getPlayerIdx() == 2) {
                    ObjectNode outputObject = newNode.putObject("output");
                    Hero.outputHero(table.getPlayerTwoHero(), outputObject);
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "getCardAtPosition":
                int x = action.getX();
                int y = action.getY();
                if(x < 0 || x >= table.getTable().size() || y < 0 || y >= table.getTable().get(0).size()) {
                    newNode.put("output", "No card available at that position.");
                } else {
                    if(table.getTable().get(x).get(y) == null) {
                        newNode.put("output", "No card available at that position.");
                    } else {
                        ObjectNode outputObject = newNode.putObject("output");
                        Minion.outputMinion(table.getTable().get(x).get(y), outputObject);
                    }
                }
                break;
            case "getPlayerMana":
                newNode.put("playerIdx", action.getPlayerIdx());
                if(action.getPlayerIdx() == 1) {
                    newNode.put("output", GameUtils.getPlayerOneMana());
                } else if(action.getPlayerIdx() == 2) {
                    newNode.put("output", GameUtils.getPlayerTwoMana());
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
        }
    }
}
