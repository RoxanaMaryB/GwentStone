package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.ActionsInput;
import game.logic.Coordinates;
import game.logic.GameTable;
import game.logic.GameUtils;

public final class CommandOrganizer {
    /**
     * Calls the appropriate command from DebugCommand or ActionCommand
     * depending on the action received
     * @param command
     */
    public void executeCommand(final Command command) {
        ActionsInput action = command.getAction();
        GameTable table = command.getTable();
        ArrayNode output = command.getOutput();
        ObjectNode newNode;
        switch (action.getCommand()) {
            case "getPlayerDeck":
                DebugCommand.getPlayerDeck(action.getPlayerIdx());
                break;
            case "getCardsInHand":
                DebugCommand.getCardsInHand(action.getPlayerIdx());
                break;
            case "getCardsOnTable":
                DebugCommand.getCardsOnTable();
                break;
            case "getPlayerTurn":
                DebugCommand.getPlayerTurn();
                break;
            case "getPlayerHero":
                DebugCommand.getPlayerHero(action.getPlayerIdx());
                break;
            case "getCardAtPosition":
                Coordinates coordinates = new Coordinates(action.getX(), action.getY());
                DebugCommand.getCardAtPosition(coordinates);
                break;
            case "getPlayerMana":
                DebugCommand.getPlayerMana(action.getPlayerIdx());
                break;
            case "endPlayerTurn":
                ActionCommand.endPlayerTurn();
                break;
            case "placeCard":
                int cardIdx = action.getHandIdx();
                int playerIdx = GameUtils.getCurrentPlayer();
                ActionCommand.placeCard(playerIdx, cardIdx);
                break;
            case "cardUsesAttack":
                Coordinates attackerCoordinates = new Coordinates(action.getCardAttacker().getX(),
                        action.getCardAttacker().getY());
                Coordinates attackedCoordinates = new Coordinates(action.getCardAttacked().getX(),
                        action.getCardAttacked().getY());
                ActionCommand.cardUsesAttack(attackerCoordinates, attackedCoordinates);
                break;
            case "cardUsesAbility":
                Coordinates userCoordinates = new Coordinates(action.getCardAttacker().getX(),
                        action.getCardAttacker().getY());
                Coordinates usedCoordinates = new Coordinates(action.getCardAttacked().getX(),
                        action.getCardAttacked().getY());
                ActionCommand.cardUsesAbility(userCoordinates, usedCoordinates);
                break;
            case "useAttackHero":
                Coordinates heroAttackercoordinates = new Coordinates(action
                        .getCardAttacker().getX(), action.getCardAttacker().getY());
                ActionCommand.useAttackHero(heroAttackercoordinates);
                break;
            case "useHeroAbility":
                int rowAffected = action.getAffectedRow();
                ActionCommand.useHeroAbility(rowAffected);
                break;
            case "getFrozenCardsOnTable":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                ArrayNode outputArrayFrozen = newNode.putArray("output");
                table.outputFrozen(outputArrayFrozen);
                break;
            case "getTotalGamesPlayed":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("output", GameUtils.getNrGamesSoFar());
                break;
            case "getPlayerOneWins":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("output", GameUtils.getPlayerOneWins());
                break;
            case "getPlayerTwoWins":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("output", GameUtils.getPlayerTwoWins());
                break;
            default:
                break;
        }
    }
}
