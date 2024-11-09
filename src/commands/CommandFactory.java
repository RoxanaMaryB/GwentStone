package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cards.*;
import fileio.ActionsInput;
import game.logic.Coordinates;
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
                newNode.put("x", x);
                newNode.put("y", y);
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
                table.defrostMinions(3 - GameUtils.getCurrentPlayer());
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
            case "cardUsesAttack":
                Coordinates attackerCoordinates = new Coordinates(action.getCardAttacker().getX(), action.getCardAttacker().getY());
                Coordinates attackedCoordinates = new Coordinates(action.getCardAttacked().getX(), action.getCardAttacked().getY());
                if(badCoordinates(attackerCoordinates, table) || badCoordinates(attackedCoordinates, table)) {
                    outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates, "No card available at this position", output);
                    break;
                }
                Minion Attacker = table.getTable().get(attackerCoordinates.getX()).get(attackerCoordinates.getY());
                Minion Attacked = table.getTable().get(attackedCoordinates.getX()).get(attackedCoordinates.getY());
                // attacker is player 1, attacked is player 2 => row attacked is 0 or 1
                if(!attackEnemy(GameUtils.getCurrentPlayer(), attackedCoordinates.getX())) {
                    outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates, "Attacked card does not belong to the enemy.", output);
                    break;
                }
                if(Attacker.isHasAttacked()) {
                    outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates, "Attacker card has already attacked this turn.", output);
                    break;
                }
                if(Attacker.isFrozen()) {
                    outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates, "Attacker card is frozen.", output);
                    break;
                }
                if(!Attacked.isTank() && table.checkTanksPresent(3 - GameUtils.getCurrentPlayer())) {
                    outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates, "Attacked card is not of type 'Tank'.", output);
                    break;
                }
                Attacker.setHasAttacked(true);
                Attacked.setHealth(Math.max(0, Attacked.getHealth() - Attacker.getAttackDamage()));
                if(Attacked.getHealth() <= 0) {
                    table.getTable().get(attackedCoordinates.getX()).remove(attackedCoordinates.getY());
                }
                break;
            case "cardUsesAbility":
                Coordinates userCoordinates = new Coordinates(action.getCardAttacker().getX(), action.getCardAttacker().getY());
                Coordinates usedCoordinates = new Coordinates(action.getCardAttacked().getX(), action.getCardAttacked().getY());
                if(badCoordinates(userCoordinates, table) || badCoordinates(usedCoordinates, table)) {
                    outputError("cardUsesAbility", userCoordinates, usedCoordinates, "No card available at this position", output);
                    break;
                }
                Minion User = table.getTable().get(userCoordinates.getX()).get(userCoordinates.getY());
                Minion Used = table.getTable().get(usedCoordinates.getX()).get(usedCoordinates.getY());
                if(User.isFrozen()) {
                    outputError("cardUsesAbility", userCoordinates, usedCoordinates, "Attacker card is frozen.", output);
                    break;
                }
                if(User.isHasAttacked()) {
                    outputError("cardUsesAbility", userCoordinates, usedCoordinates, "Attacker card has already attacked this turn.", output);
                    break;
                }
                if(User.getName().equals("Disciple") && !attackOwn(GameUtils.getCurrentPlayer(), usedCoordinates.getX())) {
                    outputError("cardUsesAbility", userCoordinates, usedCoordinates, "Attacked card does not belong to the current player.", output);
                    break;
                }
                if(User.getName().equals("The Ripper") || User.getName().equals("Miraj") || User.getName().equals("The Cursed One")) {
                    if(!attackEnemy(GameUtils.getCurrentPlayer(), usedCoordinates.getX())) {
                        outputError("cardUsesAbility", userCoordinates, usedCoordinates, "Attacked card does not belong to the enemy.", output);
                        break;
                    }
                    if(!Used.isTank() && table.checkTanksPresent(3 - GameUtils.getCurrentPlayer())) {
                        outputError("cardUsesAbility", userCoordinates, usedCoordinates, "Attacked card is not of type 'Tank'.", output);
                        break;
                    }
                }
                User.setHasAttacked(true);
                switch(User.getName()) {
                    case "Disciple":
                        Used.setHealth(Used.getHealth() + 2);
                        break;
                    case "The Ripper":
                        Used.setAttackDamage(Math.max(Used.getAttackDamage() - 2, 0));
                        break;
                    case "Miraj":
                        int temp = Used.getHealth();
                        Used.setHealth(User.getHealth());
                        User.setHealth(temp);
                        break;
                    case "The Cursed One":
                        int temp2 = Used.getHealth();
                        Used.setHealth(Used.getAttackDamage());
                        Used.setAttackDamage(temp2);
                        break;
                }
                if(Used.getHealth() <= 0) {
                    table.getTable().get(usedCoordinates.getX()).remove(usedCoordinates.getY());
                }
                break;

            case "useAttackHero":
                Coordinates heroAttackercoordinates = new Coordinates(action.getCardAttacker().getX(), action.getCardAttacker().getY());
                if(badCoordinates(heroAttackercoordinates, table)) {
                    outputHeroError("useAttackHero", heroAttackercoordinates, "No card available at this position", output);
                    break;
                }
                Minion HeroAttacker = table.getTable().get(heroAttackercoordinates.getX()).get(heroAttackercoordinates.getY());
                Hero heroAttacked = GameUtils.getCurrentPlayer() == 1 ? table.getPlayerTwoHero() : table.getPlayerOneHero();
                if(HeroAttacker.isFrozen()) {
                    outputHeroError("useAttackHero", heroAttackercoordinates, "Attacker card is frozen.", output);
                    break;
                }
                if(HeroAttacker.isHasAttacked()) {
                    outputHeroError("useAttackHero", heroAttackercoordinates, "Attacker card has already attacked this turn.", output);
                    break;
                }
                if(table.checkTanksPresent(3 - GameUtils.getCurrentPlayer())) {
                    outputHeroError("useAttackHero", heroAttackercoordinates, "Attacked card is not of type 'Tank'.", output);
//                    System.out.println("Hero attacked " + heroAttacked.getName() + " by " + HeroAttacker.getName());
//                    System.out.println("Attacker coords " + heroAttackercoordinates.getX() + " " + heroAttackercoordinates.getY());
                    // print whole table with all minions row by row
                    for(int i = 0; i < table.getTable().size(); i++) {
                        for(int j = 0; j < table.getTable().get(i).size(); j++) {
                            System.out.println(table.getTable().get(i).get(j).getName());
                        }
                        System.out.println();
                    }
                    break;
                }
                HeroAttacker.setHasAttacked(true);
                heroAttacked.setHealth(Math.max(0, heroAttacked.getHealth() - HeroAttacker.getAttackDamage()));
                if(heroAttacked.getHealth() <= 0) {
                    GameUtils.setNrGamesSoFar(GameUtils.getNrGamesSoFar() + 1);
                    if(GameUtils.getCurrentPlayer() == 1) {
                        GameUtils.setPlayerOneWins(GameUtils.getPlayerOneWins() + 1);
                        GameUtils.setGameOver(true);
                        ObjectNode newNodeWin = output.addObject();
                        newNodeWin.put("gameEnded", "Player one killed the enemy hero.");
                    } else {
                        GameUtils.setPlayerTwoWins(GameUtils.getPlayerTwoWins() + 1);
                        GameUtils.setGameOver(true);
                        ObjectNode newNodeWin = output.addObject();
                        newNodeWin.put("gameEnded", "Player two killed the enemy hero.");
                    }
                }
                break;
            case "useHeroAbility":
                int rowAffected = action.getAffectedRow();
                if(rowAffected < 0 || rowAffected >= table.getTable().size()) {
                    newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Invalid row index.");
                    break;
                }
                Hero hero = GameUtils.getCurrentPlayer() == 1 ? table.getPlayerOneHero() : table.getPlayerTwoHero();
                int manaPlayer = GameUtils.getCurrentPlayer() == 1 ? GameUtils.getPlayerOneMana() : GameUtils.getPlayerTwoMana();
                if(manaPlayer < hero.getMana()) {
                    newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Not enough mana to use hero's ability.");
                    break;
                }
                if(hero.isHasAttacked()) {
                    newNode = output.addObject();
                    newNode.put("command", action.getCommand());
                    newNode.put("affectedRow", action.getAffectedRow());
                    newNode.put("error", "Hero has already attacked this turn.");
                    break;
                }
                if(hero.getName().equals("Empress Thorina") || hero.getName().equals("Lord Royce")) {
                    if (GameUtils.getCurrentPlayer() == 1 && rowAffected != 0 && rowAffected != 1 ||
                            GameUtils.getCurrentPlayer() == 2 && rowAffected != 2 && rowAffected != 3) {
                        newNode = output.addObject();
                        newNode.put("command", action.getCommand());
                        newNode.put("affectedRow", action.getAffectedRow());
                        newNode.put("error", "Selected row does not belong to the enemy.");
                        break;
                    }
                } else if(hero.getName().equals("General Kocioraw") || hero.getName().equals("King Mudface")) {
                    if (GameUtils.getCurrentPlayer() == 1 && rowAffected != 2 && rowAffected != 3 ||
                            GameUtils.getCurrentPlayer() == 2 && rowAffected != 0 && rowAffected != 1) {
                        newNode = output.addObject();
                        newNode.put("command", action.getCommand());
                        newNode.put("affectedRow", action.getAffectedRow());
                        newNode.put("error", "Selected row does not belong to the current player.");
                        break;
                    }
                }
                if(GameUtils.getCurrentPlayer() == 1) {
                    GameUtils.decreaseMana(table.getPlayerOneHero().getMana(), 1);
                    table.getPlayerOneHero().setHasAttacked(true);
                } else {
                    GameUtils.decreaseMana(table.getPlayerTwoHero().getMana(), 2);
                    table.getPlayerTwoHero().setHasAttacked(true);
                }
                switch(hero.getName()) {
                    case "Empress Thorina":
                        int maxHealth = -1;
                        int indexMaxHealth = -1;
                        for(int i = 0; i < table.getTable().get(rowAffected).size(); i++) {
                            if(table.getTable().get(rowAffected).get(i).getHealth() > maxHealth) {
                                maxHealth = table.getTable().get(rowAffected).get(i).getHealth();
                                indexMaxHealth = i;
                            }
                        }
                        if(indexMaxHealth != -1) {
                            table.getTable().get(rowAffected).remove(indexMaxHealth);
                        }
                        break;
                    case "Lord Royce":
                        for(int i = 0; i < table.getTable().get(rowAffected).size(); i++) {
                            table.getTable().get(rowAffected).get(i).setFrozen(true);
                        }
                        break;
                    case "General Kocioraw":
                        for(int i = 0; i < table.getTable().get(rowAffected).size(); i++) {
                            table.getTable().get(rowAffected).get(i).setAttackDamage(table.getTable().get(rowAffected).get(i).getAttackDamage() + 1);
                        }
                        break;
                    case "King Mudface":
                        for(int i = 0; i < table.getTable().get(rowAffected).size(); i++) {
                            table.getTable().get(rowAffected).get(i).setHealth(table.getTable().get(rowAffected).get(i).getHealth() + 1);
                        }
                        break;
                }
                break;
            case "getFrozenCardsOnTable":
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                ArrayNode outputArrayFrozen = newNode.putArray("output");
                table.outputFrozen(table, outputArrayFrozen);
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
        }
    }

    public boolean attackEnemy(int player, int xAttacked) {
        if((player == 2 && xAttacked != 2 && xAttacked != 3) ||
                (player == 1 && xAttacked != 0 && xAttacked != 1))
            return false;
        return true;
    }

    public boolean attackOwn(int player, int xAttacked) {
        if((player == 1 && xAttacked != 2 && xAttacked != 3) ||
                (player == 2 && xAttacked != 0 && xAttacked != 1))
            return false;
        return true;
    }

    public boolean badCoordinates(Coordinates element, GameTable table) {
        if(element.getX() < 0 || element.getX() >= table.getTable().size() || element.getY() < 0 || element.getY() >= table.getTable().get(element.getX()).size()) {
            return true;
        }
        return false;
    }

    public void outputError(String command, Coordinates attackerCoordinates, Coordinates attackedCoordinates, String error, ArrayNode output) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", command);
        ObjectNode cardAttacker = newNode.putObject("cardAttacker");
        cardAttacker.put("x", attackerCoordinates.getX());
        cardAttacker.put("y", attackerCoordinates.getY());
        ObjectNode cardAttacked = newNode.putObject("cardAttacked");
        cardAttacked.put("x", attackedCoordinates.getX());
        cardAttacked.put("y", attackedCoordinates.getY());
        newNode.put("error", error);
    }

    public void outputHeroError(String command, Coordinates attackerCoordinates, String error, ArrayNode output) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", command);
        ObjectNode cardAttacker = newNode.putObject("cardAttacker");
        cardAttacker.put("x", attackerCoordinates.getX());
        cardAttacker.put("y", attackerCoordinates.getY());
        newNode.put("error", error);
    }


}
