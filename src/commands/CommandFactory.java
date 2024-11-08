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
        ArrayNode output = command.getOutput();
        ObjectNode newNode;
        switch(action.getCommand()) {
            case "getPlayerDeck":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    ArrayNode outputArray = newNode.putArray("output");
                    Deck.outputDeck(table.getPlayerOneDeck(), outputArray);
                } else if (action.getPlayerIdx() == 2) {
                    ArrayNode outputArray = newNode.putArray("output");
                    Deck.outputDeck(table.getPlayerTwoDeck(), outputArray);
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "getCardsInHand":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    ArrayNode outputArray = newNode.putArray("output");
                    GameTable.outputHand(table.getPlayerOneHand(), outputArray);
                } else if (action.getPlayerIdx() == 2) {
                    ArrayNode outputArray = newNode.putArray("output");
                    GameTable.outputHand(table.getPlayerTwoHand(), outputArray);
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "getCardsOnTable":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                ArrayNode outputArray = newNode.putArray("output");
                GameTable.outputTable(table.getTable(), outputArray);
                break;
            case "getPlayerTurn":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("output", GameUtils.getCurrentPlayer());
                break;
            case "getPlayerHero":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    ObjectNode outputObject = newNode.putObject("output");
                    Hero.outputHero(table.getPlayerOneHero(), outputObject);
                } else if (action.getPlayerIdx() == 2) {
                    ObjectNode outputObject = newNode.putObject("output");
                    Hero.outputHero(table.getPlayerTwoHero(), outputObject);
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "getCardAtPosition":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                int x = action.getX();
                int y = action.getY();
                if (x < 0 || x >= table.getTable().size() || y < 0 || y >= table.getTable().get(x).size()) {
                    newNode.put("output", "No card available at that position.");
                } else {
                    ObjectNode outputObject = newNode.putObject("output");
                    Minion.outputMinion(table.getTable().get(x).get(y), outputObject);
                }
                break;
            case "getPlayerMana":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("playerIdx", action.getPlayerIdx());
                if (action.getPlayerIdx() == 1) {
                    newNode.put("output", GameUtils.getPlayerOneMana());
                } else if (action.getPlayerIdx() == 2) {
                    newNode.put("output", GameUtils.getPlayerTwoMana());
                } else {
                    newNode.put("output", "Invalid player index");
                }
                break;
            case "endPlayerTurn":
                GameUtils.setNrTurn(GameUtils.getNrTurn() + 1);
                GameUtils.setCurrentPlayer(3 - GameUtils.getCurrentPlayer());
                if (GameUtils.getNrTurn() % 2 == 0) {
                    table.addMinionToHand(1);
                    table.addMinionToHand(2);
                    int addedMana = GameUtils.getNrTurn() / 2 + 1;
                    GameUtils.increaseMana(addedMana, 1);
                    GameUtils.increaseMana(addedMana, 2);
                }
                table.defrostMinions(GameUtils.getCurrentPlayer());
                table.getPlayerOneHero().setHasAttacked(false);
                table.getPlayerTwoHero().setHasAttacked(false);
                table.resetMinions();
                break;
            case "placeCard":
                int index = action.getHandIdx();
                int player = GameUtils.getCurrentPlayer();
                int row = 0;
                if(player == 1) {
                    if(index < 0 || index >= table.getPlayerOneHand().size()) {
                        newNode = output.addObject();
                        newNode.put("command", action.getCommand());
                        newNode.put("error", "Invalid index for player 1 hand.");
                        newNode.put("handIdx", action.getHandIdx());
                        break;
                    }
                    row = table.getPlayerOneHand().get(index).getRow() + 2;
                    if(GameUtils.getPlayerOneMana() < table.getPlayerOneHand().get(index).getMana()) {
                        newNode = output.addObject();
                        newNode.put("command", action.getCommand());
                        newNode.put("error", "Not enough mana to place card on table.");
                        newNode.put("handIdx", action.getHandIdx());
                        break;
                    }
                } else if(player == 2) {
                    if(index < 0 || index >= table.getPlayerTwoHand().size()) {
                        newNode = output.addObject();
                        newNode.put("command", action.getCommand());
                        newNode.put("error", "Invalid index for player 2 hand.");
                        newNode.put("handIdx", action.getHandIdx());
                        break;
                    }
                    row = 1 - table.getPlayerTwoHand().get(index).getRow();
                    if(GameUtils.getPlayerTwoMana() < table.getPlayerTwoHand().get(index).getMana()) {
                        newNode = output.addObject();
                        newNode.put("command", action.getCommand());
                        newNode.put("error", "Not enough mana to place card on table.");
                        newNode.put("handIdx", action.getHandIdx());
                        break;
                    }
                }
                if(table.checkFullRow(row)) {
                    newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("error", "Cannot place card on table since row is full.");
                    newNode.put("handIdx", action.getHandIdx());
                    break;
                }
                if(player == 1) {
                    table.getTable().get(row).add(table.getPlayerOneHand().get(index));
                    GameUtils.decreaseMana(table.getPlayerOneHand().get(index).getMana(), 1);
                    table.getPlayerOneHand().remove(index);
                } else {
                    table.getTable().get(row).add(table.getPlayerTwoHand().get(index));
                    GameUtils.decreaseMana(table.getPlayerTwoHand().get(index).getMana(), 2);
                    table.getPlayerTwoHand().remove(index);
                }
                break;
//            case "cardUsesAttack":
//                int xAttacker = action.getCardAttacker().getX();
//                int yAttacker = action.getCardAttacker().getY();
//                Card Attacker = table.getTable().get(xAttacker).get(y);
//                if(Attacker == null) {
//                    newNode = output.addObject();
//                    newNode.put("command", action.getCommand());
//                    newNode.put("error", "No card available at that position.");
//                    break;
//                }
//

        }
    }
}
