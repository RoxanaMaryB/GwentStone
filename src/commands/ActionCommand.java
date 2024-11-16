package commands;

import cards.Hero;
import cards.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.logic.Coordinates;
import game.logic.GameTable;
import game.logic.GameUtils;

public class ActionCommand extends Command {

    private static final int CHANGE_PLAYER = 3;
    private static final int PLAYER_TWO_BACK = 0;
    private static final int PLAYER_TWO_FRONT = 1;
    private static final int PLAYER_ONE_FRONT = 2;
    private static final int PLAYER_ONE_BACK = 3;

    public ActionCommand(final ActionsInput action, final GameTable table, final ArrayNode output) {
        super(action, table, output);
    }

    /**
     * Ends current player's turn and starts the other player's turn.
     * Adds a minion to each player's hand and increases their mana after a round.
     * Defrosts the minions of the other player.
     */
    public static void endPlayerTurn() {
        CommandContext context = CommandContext.getInstance();
        GameUtils.setNrTurn(GameUtils.getNrTurn() + 1);
        GameUtils.setCurrentPlayer(CHANGE_PLAYER - GameUtils.getCurrentPlayer());
        if (GameUtils.getNrTurn() % 2 == 0) {
            context.getTable().addMinionToHand(1);
            context.getTable().addMinionToHand(2);
            int addedMana = GameUtils.getNrTurn() / 2 + 1;
            GameUtils.increaseMana(addedMana, 1);
            GameUtils.increaseMana(addedMana, 2);
        }
        context.getTable().defrostMinions(CHANGE_PLAYER - GameUtils.getCurrentPlayer());
        context.getTable().getPlayerOneHero().setHasAttacked(false);
        context.getTable().getPlayerTwoHero().setHasAttacked(false);
        context.getTable().resetMinions();
    }

    private static boolean validateCardIdx(final int playerIdx, final int cardIdx) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode;
        if (playerIdx == 1) {
            if (cardIdx < 0 || cardIdx >= context.getTable().getPlayerOneHand().size()) {
                newNode = context.getOutput().addObject();
                newNode.put("command", context.getAction().getCommand());
                newNode.put("error", "Invalid index for player 1 hand.");
                newNode.put("handIdx", cardIdx);
                return false;
            }
        } else if (playerIdx == 2) {
            if (cardIdx < 0 || cardIdx >= context.getTable().getPlayerTwoHand().size()) {
                newNode = context.getOutput().addObject();
                newNode.put("command", context.getAction().getCommand());
                newNode.put("error", "Invalid index for player 2 hand.");
                newNode.put("handIdx", cardIdx);
                return false;
            }
        }
        return true;
    }

    private static boolean checkMana(final int playerIdx, final int cardIdx) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode;
        if (playerIdx == 1) {
            if (GameUtils.getPlayerOneMana() < context.getTable()
                    .getPlayerOneHand().get(cardIdx).getMana()) {
                newNode = context.getOutput().addObject();
                newNode.put("command", context.getAction().getCommand());
                newNode.put("error", "Not enough mana to place card on table.");
                newNode.put("handIdx", cardIdx);
                return false;
            }
        } else if (playerIdx == 2) {
            if (GameUtils.getPlayerTwoMana() < context.getTable()
                    .getPlayerTwoHand().get(cardIdx).getMana()) {
                newNode = context.getOutput().addObject();
                newNode.put("command", context.getAction().getCommand());
                newNode.put("error", "Not enough mana to place card on table.");
                newNode.put("handIdx", cardIdx);
                return false;
            }
        }
        return true;
    }

    private static boolean checkFullRow(final int row) {
        CommandContext context = CommandContext.getInstance();
        ObjectNode newNode;
        if (context.getTable().checkFullRow(row)) {
            newNode = context.getOutput().addObject();
            newNode.put("command", context.getAction().getCommand());
            newNode.put("error", "Cannot place card on table since row is full.");
            newNode.put("handIdx", row);
            return false;
        }
        return true;
    }

    /**
     * Places a card from a player's hand on the table.
     * Decreases the player's mana and removes the card from the player's hand.
     * @param playerIdx
     * @param cardIdx
     */
    public static void placeCard(final int playerIdx, final int cardIdx) {
        CommandContext context = CommandContext.getInstance();
        if (!validateCardIdx(playerIdx, cardIdx)) {
            return;
        }
        if (!checkMana(playerIdx, cardIdx)) {
            return;
        }
        int row = 0;
        if (playerIdx == 1) {
            row = context.getTable().getPlayerOneHand().get(cardIdx).getRow() + 2;
        } else {
            row = 1 - context.getTable().getPlayerTwoHand().get(cardIdx).getRow();
        }
        if (!checkFullRow(row)) {
            return;
        }
        if (playerIdx == 1) {
            context.getTable().getTable().get(row).add(context.getTable()
                    .getPlayerOneHand().get(cardIdx));
            GameUtils.decreaseMana(context.getTable().getPlayerOneHand()
                    .get(cardIdx).getMana(), 1);
            context.getTable().getPlayerOneHand().remove(cardIdx);
        } else {
            context.getTable().getTable().get(row).add(context.getTable()
                    .getPlayerTwoHand().get(cardIdx));
            GameUtils.decreaseMana(context.getTable().getPlayerTwoHand()
                    .get(cardIdx).getMana(), 2);
            context.getTable().getPlayerTwoHand().remove(cardIdx);
        }
    }

    /**
     * Checks if the coordinates are valid.
     * @param element
     * @param table
     * @return
     */
    public static boolean badCoordinates(final Coordinates element, final GameTable table) {
        return element.getX() < 0 || element.getX() >= table.getTable().size()
                || element.getY() < 0 || element.getY() >= table.getTable()
                .get(element.getX()).size();
    }

    /**
     * Checks if the attacker is trying to attack the enemy
     * @param player
     * @param xAttacked
     * @return
     */
    public static boolean attackEnemy(final int player, final int xAttacked) {
        if (player == 1) {
            return xAttacked == PLAYER_TWO_FRONT || xAttacked == PLAYER_TWO_BACK;
        } else {
            return xAttacked == PLAYER_ONE_FRONT || xAttacked == PLAYER_ONE_BACK;
        }
    }

    /**
     * Outputs an error message for the attack commands.
     * @param command
     * @param attackerCoordinates
     * @param attackedCoordinates
     * @param error
     * @param output
     */
    public static void outputError(final String command, final Coordinates attackerCoordinates,
                                   final Coordinates attackedCoordinates, final String error,
                                   final ArrayNode output) {
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

    /**
     * Uses a card to attack another card.
     * Decreases the attacked card's health and removes it if its health is 0.
     * Checks if the attack is valid.
     * @param attackerCoordinates
     * @param attackedCoordinates
     */
    public static void cardUsesAttack(final Coordinates attackerCoordinates,
                                      final Coordinates attackedCoordinates) {
        CommandContext context = CommandContext.getInstance();
        GameTable table = context.getTable();
        ArrayNode output = context.getOutput();
        if (badCoordinates(attackerCoordinates, table)
                || badCoordinates(attackedCoordinates, table)) {
            outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates,
                    "No card available at this position", output);
            return;
        }
        Minion attacker = table.getTable().get(attackerCoordinates.getX())
                .get(attackerCoordinates.getY());
        Minion attacked = table.getTable().get(attackedCoordinates.getX())
                .get(attackedCoordinates.getY());
        // attacker is player 1, attacked is player 2 => row attacked is 0 or 1
        if (!attackEnemy(GameUtils.getCurrentPlayer(), attackedCoordinates.getX())) {
            outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates,
                    "Attacked card does not belong to the enemy.", output);
            return;
        }
        if (attacker.isHasAttacked()) {
            outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates,
                    "Attacker card has already attacked this turn.", output);
            return;
        }
        if (attacker.isFrozen()) {
            outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates,
                    "Attacker card is frozen.", output);
            return;
        }
        if (!attacked.isTank()
                && table.checkTanksPresent(CHANGE_PLAYER - GameUtils.getCurrentPlayer())) {
            outputError("cardUsesAttack", attackerCoordinates, attackedCoordinates,
                    "Attacked card is not of type 'Tank'.", output);
            return;
        }
        attacker.setHasAttacked(true);
        attacked.setHealth(Math.max(0, attacked.getHealth() - attacker.getAttackDamage()));
        if (attacked.getHealth() <= 0) {
            table.getTable().get(attackedCoordinates.getX()).remove(attackedCoordinates.getY());
        }
    }

    /**
     * Uses the ability of a card on another card.
     * Changes the health or attack damage of the attacked card.
     * Checks if the ability is valid.
     * @param userCoordinates
     * @param usedCoordinates
     */
    public static void cardUsesAbility(final Coordinates userCoordinates,
                                       final Coordinates usedCoordinates) {
        CommandContext context = CommandContext.getInstance();
        GameTable table = context.getTable();
        ArrayNode output = context.getOutput();
        if (badCoordinates(userCoordinates, table) || badCoordinates(usedCoordinates, table)) {
            outputError("cardUsesAbility", userCoordinates, usedCoordinates,
                    "No card available at this position", output);
            return;
        }
        Minion user = table.getTable().get(userCoordinates.getX()).get(userCoordinates.getY());
        Minion used = table.getTable().get(usedCoordinates.getX()).get(usedCoordinates.getY());
        if (user.isFrozen()) {
            outputError("cardUsesAbility", userCoordinates, usedCoordinates,
                    "Attacker card is frozen.", output);
            return;
        }
        if (user.isHasAttacked()) {
            outputError("cardUsesAbility", userCoordinates, usedCoordinates,
                    "Attacker card has already attacked this turn.", output);
            return;
        }
        if (user.getName().equals("Disciple") && attackEnemy(GameUtils.getCurrentPlayer(),
                usedCoordinates.getX())) {
            outputError("cardUsesAbility", userCoordinates, usedCoordinates,
                    "Attacked card does not belong to the current player.", output);
            return;
        }
        if (user.getName().equals("The Ripper") || user.getName().equals("Miraj")
                || user.getName().equals("The Cursed One")) {
            if (!attackEnemy(GameUtils.getCurrentPlayer(), usedCoordinates.getX())) {
                outputError("cardUsesAbility", userCoordinates, usedCoordinates,
                        "Attacked card does not belong to the enemy.", output);
                return;
            }
            if (!used.isTank()
                    && table.checkTanksPresent(CHANGE_PLAYER - GameUtils.getCurrentPlayer())) {
                outputError("cardUsesAbility", userCoordinates, usedCoordinates,
                        "Attacked card is not of type 'Tank'.", output);
                return;
            }
        }
        user.setHasAttacked(true);
        switch (user.getName()) {
            case "Disciple":
                used.setHealth(used.getHealth() + 2);
                break;
            case "The Ripper":
                used.setAttackDamage(Math.max(used.getAttackDamage() - 2, 0));
                break;
            case "Miraj":
                int temp = used.getHealth();
                used.setHealth(user.getHealth());
                user.setHealth(temp);
                break;
            case "The Cursed One":
                int temp2 = used.getHealth();
                used.setHealth(used.getAttackDamage());
                used.setAttackDamage(temp2);
                break;
            default:
                break;
        }
        if (used.getHealth() <= 0) {
            table.getTable().get(usedCoordinates.getX()).remove(usedCoordinates.getY());
        }
    }

    /**
     * Outputs an error message for the hero attack commands.
     * @param command
     * @param attackerCoordinates
     * @param error
     * @param output
     */
    public static void outputHeroError(final String command, final Coordinates attackerCoordinates,
                                       final String error, final ArrayNode output) {
        ObjectNode newNode = output.addObject();
        newNode.put("command", command);
        ObjectNode cardAttacker = newNode.putObject("cardAttacker");
        cardAttacker.put("x", attackerCoordinates.getX());
        cardAttacker.put("y", attackerCoordinates.getY());
        newNode.put("error", error);
    }

    /**
     * Uses a card to attack the enemy hero.
     * Decreases the hero's health and ends the game if the hero's health is 0.
     * @param heroAttackerCoordinates
     */
    public static void useAttackHero(final Coordinates heroAttackerCoordinates) {
        CommandContext context = CommandContext.getInstance();
        GameTable table = context.getTable();
        ArrayNode output = context.getOutput();
        if (badCoordinates(heroAttackerCoordinates, table)) {
            outputHeroError("useAttackHero", heroAttackerCoordinates,
                    "No card available at this position", output);
            return;
        }
        Minion heroAttacker = table.getTable().get(heroAttackerCoordinates.getX())
                .get(heroAttackerCoordinates.getY());
        Hero heroAttacked = GameUtils.getCurrentPlayer() == 1 ? table.getPlayerTwoHero()
                : table.getPlayerOneHero();
        if (heroAttacker.isFrozen()) {
            outputHeroError("useAttackHero", heroAttackerCoordinates,
                    "Attacker card is frozen.", output);
            return;
        }
        if (heroAttacker.isHasAttacked()) {
            outputHeroError("useAttackHero", heroAttackerCoordinates,
                    "Attacker card has already attacked this turn.", output);
            return;
        }
        if (table.checkTanksPresent(CHANGE_PLAYER - GameUtils.getCurrentPlayer())) {
            outputHeroError("useAttackHero", heroAttackerCoordinates,
                    "Attacked card is not of type 'Tank'.", output);
            for (int i = 0; i < table.getTable().size(); i++) {
                for (int j = 0; j < table.getTable().get(i).size(); j++) {
                    System.out.println(table.getTable().get(i).get(j).getName());
                }
                System.out.println();
            }
            return;
        }
        heroAttacker.setHasAttacked(true);
        int restHealth = heroAttacked.getHealth() - heroAttacker.getAttackDamage();
        heroAttacked.setHealth(Math.max(0, restHealth));
        if (heroAttacked.getHealth() <= 0) {
            GameUtils.setNrGamesSoFar(GameUtils.getNrGamesSoFar() + 1);
            if (GameUtils.getCurrentPlayer() == 1) {
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
    }

    /**
     * Uses the hero's ability on a row.
     * Changes the health or attack damage of the cards in the row.
     * @param row
     */
    public static void useHeroAbility(final int row) {
        CommandContext context = CommandContext.getInstance();
        GameTable table = context.getTable();
        ArrayNode output = context.getOutput();
        ActionsInput action = context.getAction();
        ObjectNode newNode;
        if (row < 0 || row >= table.getTable().size()) {
            newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("affectedRow", action.getAffectedRow());
            newNode.put("error", "Invalid row index.");
            return;
        }
        Hero hero = GameUtils.getCurrentPlayer() == 1 ? table.getPlayerOneHero()
                : table.getPlayerTwoHero();
        int manaPlayer = GameUtils.getCurrentPlayer() == 1 ? GameUtils.getPlayerOneMana()
                : GameUtils.getPlayerTwoMana();
        if (manaPlayer < hero.getMana()) {
            newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("affectedRow", action.getAffectedRow());
            newNode.put("error", "Not enough mana to use hero's ability.");
            return;
        }
        if (hero.isHasAttacked()) {
            newNode = output.addObject();
            newNode.put("command", action.getCommand());
            newNode.put("affectedRow", action.getAffectedRow());
            newNode.put("error", "Hero has already attacked this turn.");
            return;
        }
        if (hero.getName().equals("Empress Thorina") || hero.getName().equals("Lord Royce")) {
            if (!attackEnemy(GameUtils.getCurrentPlayer(), row)) {
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("affectedRow", action.getAffectedRow());
                newNode.put("error", "Selected row does not belong to the enemy.");
                return;
            }
        } else if (hero.getName().equals("General Kocioraw")
                || hero.getName().equals("King Mudface")) {
            if (attackEnemy(GameUtils.getCurrentPlayer(), row)) {
                newNode = output.addObject();
                newNode.put("command", action.getCommand());
                newNode.put("affectedRow", action.getAffectedRow());
                newNode.put("error", "Selected row does not belong to the current player.");
                return;
            }
        }
        if (GameUtils.getCurrentPlayer() == 1) {
            GameUtils.decreaseMana(table.getPlayerOneHero().getMana(), 1);
            table.getPlayerOneHero().setHasAttacked(true);
        } else {
            GameUtils.decreaseMana(table.getPlayerTwoHero().getMana(), 2);
            table.getPlayerTwoHero().setHasAttacked(true);
        }
        switch (hero.getName()) {
            case "Empress Thorina":
                int maxHealth = -1;
                int indexMaxHealth = -1;
                for (int i = 0; i < table.getTable().get(row).size(); i++) {
                    if (table.getTable().get(row).get(i).getHealth() > maxHealth) {
                        maxHealth = table.getTable().get(row).get(i).getHealth();
                        indexMaxHealth = i;
                    }
                }
                if (indexMaxHealth != -1) {
                    table.getTable().get(row).remove(indexMaxHealth);
                }
                break;
            case "Lord Royce":
                for (int i = 0; i < table.getTable().get(row).size(); i++) {
                    table.getTable().get(row).get(i).setFrozen(true);
                }
                break;
            case "General Kocioraw":
                for (int i = 0; i < table.getTable().get(row).size(); i++) {
                    table.getTable().get(row).get(i)
                            .setAttackDamage(table.getTable()
                                    .get(row).get(i).getAttackDamage() + 1);
                }
                break;
            case "King Mudface":
                for (int i = 0; i < table.getTable().get(row).size(); i++) {
                    table.getTable().get(row).get(i)
                            .setHealth(table.getTable().get(row).get(i).getHealth() + 1);
                }
                break;
            default:
                break;
        }
    }
}
