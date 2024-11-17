package game.logic;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import cards.Minion;
import cards.Hero;
import cards.Deck;

public final class GameTable {
    private final int rows = 4;
    private final int columns = 5;
    private ArrayList<ArrayList<Minion>> table;

    private Deck playerOneDeck;
    private Deck playerTwoDeck;

    private ArrayList<Minion> playerOneHand = new ArrayList<>();
    private ArrayList<Minion> playerTwoHand = new ArrayList<>();

    private Hero playerOneHero;
    private Hero playerTwoHero;

    public GameTable() {
        table = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayList<Minion> row = new ArrayList<>(columns);
            table.add(row);
        }
    }

    /**
     * Outputs every minion in an array of minions
     * @param hand
     * @param outputNode
     */
    public static void outputHand(final ArrayList<Minion> hand, final ArrayNode outputNode) {
        for (Minion minion : hand) {
            ObjectNode minionNode = outputNode.addObject();
            Minion.outputMinion(minion, minionNode);
        }
    }

    /**
     * Outputs the table of minions
     * @param table
     * @param outputNode
     */
    public static void outputTable(final ArrayList<ArrayList<Minion>> table,
                                   final ArrayNode outputNode) {
        for (ArrayList<Minion> row : table) {
            ArrayNode rowNode = outputNode.addArray();
            for (Minion minion : row) {
                if (minion == null) {
                    rowNode.addNull();
                } else {
                    ObjectNode minionNode = rowNode.addObject();
                    Minion.outputMinion(minion, minionNode);
                }
            }
        }
    }

    /**
     * Adds minion from deck to hand of player with index playerIdx at the beginning of the game
     * @param playerIdx
     */
    public void addMinionToHand(final int playerIdx) {
        if (playerIdx == 1 && !playerOneDeck.getMinions().isEmpty()) {
            playerOneHand.add(playerOneDeck.getMinions().get(0));
            playerOneDeck.getMinions().remove(0);
        } else if (playerIdx == 2 && !playerTwoDeck.getMinions().isEmpty()) {
            playerTwoHand.add(playerTwoDeck.getMinions().get(0));
            playerTwoDeck.getMinions().remove(0);
        }
    }

    /**
     * Checks if the row is full
     * @param row
     * @return
     */
    public boolean checkFullRow(final int row) {
        if (table.get(row).size() >= columns) {
            return true;
        }
        return false;
    }

    /**
     * Defrosts the minions of a player
     * @param player
     */
    public void defrostMinions(final int player) {
        int playerRow = player == 1 ? 2 : 0;
        for (int i = playerRow; i <= playerRow + 1; i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) != null) {
                    table.get(i).get(j).setFrozen(false);
                }
            }
        }
    }

    /**
     * Outputs the frozen minions of a player
     * @param outputNode
     */
    public void outputFrozen(final ArrayNode outputNode) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) != null && table.get(i).get(j).isFrozen()) {
                    ObjectNode minionNode = outputNode.addObject();
                    Minion.outputMinion(table.get(i).get(j), minionNode);
                }
            }
        }
    }

    /**
     * Resets the hasAttacked attribute of all minions at the end of round
     */
    public void resetMinions() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) != null) {
                    table.get(i).get(j).setHasAttacked(false);
                }
            }
        }
    }

    /**
     * Checks if there are tanks present on the side of a player
     * @param player
     * @return
     */
    public boolean checkTanksPresent(final int player) {
        int frontRow = player == 1 ? 2 : 1;
        for (int j = 0; j < table.get(frontRow).size(); j++) {
            if (table.get(frontRow).get(j) != null && table.get(frontRow).get(j).isTank()) {
                return true;
            }
        }
        return false;
    }

    public Deck getPlayerOneDeck() {
        return playerOneDeck;
    }

    public void setPlayerOneDeck(final Deck playerOneDeck) {
        this.playerOneDeck = playerOneDeck;
    }

    public Deck getPlayerTwoDeck() {
        return playerTwoDeck;
    }

    public void setPlayerTwoDeck(final Deck playerTwoDeck) {
        this.playerTwoDeck = playerTwoDeck;
    }

    public Hero getPlayerOneHero() {
        return playerOneHero;
    }

    public void setPlayerOneHero(final Hero playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    public Hero getPlayerTwoHero() {
        return playerTwoHero;
    }

    public void setPlayerTwoHero(final Hero playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    public ArrayList<Minion> getPlayerOneHand() {
        return playerOneHand;
    }

    public void setPlayerOneHand(final ArrayList<Minion> playerOneHand) {
        this.playerOneHand = playerOneHand;
    }

    public ArrayList<Minion> getPlayerTwoHand() {
        return playerTwoHand;
    }

    public void setPlayerTwoHand(final ArrayList<Minion> playerTwoHand) {
        this.playerTwoHand = playerTwoHand;
    }

    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    public void setTable(final ArrayList<ArrayList<Minion>> table) {
        this.table = table;
    }
}
