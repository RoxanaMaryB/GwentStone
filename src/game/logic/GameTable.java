package game.logic;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.util.ArrayList;

import cards.*;

public class GameTable {
    // create table of 4x5
    private final int rows = 4;
    private final int columns = 5;
    protected ArrayList<ArrayList<Minion>> table;

    protected Deck playerOneDeck;
    protected Deck playerTwoDeck;

    protected ArrayList<Minion> playerOneHand = new ArrayList<>();
    protected ArrayList<Minion> playerTwoHand = new ArrayList<>();

    private Hero playerOneHero;
    private Hero playerTwoHero;

    public GameTable() {
        table = new ArrayList<>(rows);
        for(int i = 0; i < rows; i++) {
            ArrayList<Minion> row = new ArrayList<>(columns);
            table.add(row);
        }
    }

    public static void outputHand(ArrayList<Minion> hand, ArrayNode outputNode) {
        for (Minion minion : hand) {
            ObjectNode minionNode = outputNode.addObject();
            Minion.outputMinion(minion, minionNode);
        }
    }

    public static void outputTable(ArrayList<ArrayList<Minion>> table, ArrayNode outputNode) {
        for (ArrayList<Minion> row : table) {
            ArrayNode rowNode = outputNode.addArray();
            for (Minion minion : row) {
                if(minion == null) {
                    rowNode.addNull();
                } else {
                    ObjectNode minionNode = rowNode.addObject();
                    Minion.outputMinion(minion, minionNode);
                }
            }
        }
    }

    public void addMinionToHand(int playerIdx) {
        if(playerIdx == 1 && !playerOneDeck.getMinions().isEmpty()) {
            playerOneHand.add(playerOneDeck.getMinions().get(0));
            playerOneDeck.getMinions().remove(0);
        } else if(playerIdx == 2 && !playerTwoDeck.getMinions().isEmpty()) {
            playerTwoHand.add(playerTwoDeck.getMinions().get(0));
            playerTwoDeck.getMinions().remove(0);
        }
    }

    public boolean checkFullRow(int row) {
        if(table.get(row).size() >= 5)
            return true;
        return false;
    }

    public void defrostMinions(int player) {
        int playerRow = player == 1 ? 2 : 0; // daca e player 1, defrost r 2 si 3, daca e player 2, defrost r 0 si 1
        for(int i = playerRow; i <= playerRow + 1; i++) {
            for(int j = 0; j < table.get(i).size(); j++) {
                if(table.get(i).get(j) != null) {
                    table.get(i).get(j).setFrozen(false);
                }
            }
        }
    }

    public void outputFrozen(GameTable table, ArrayNode outputNode) {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < table.getTable().get(i).size(); j++) {
                if(table.getTable().get(i).get(j) != null && table.getTable().get(i).get(j).isFrozen()) {
                    ObjectNode minionNode = outputNode.addObject();
                    Minion.outputMinion(table.getTable().get(i).get(j), minionNode);
                }
            }
        }
    }

    public void resetMinions() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < table.get(i).size(); j++) {
                if(table.get(i).get(j) != null) {
                    table.get(i).get(j).setHasAttacked(false);
                }
            }
        }
    }

    public boolean checkTanksPresent(int player) {
        int frontRow = player == 1 ? 2 : 1;
        for(int j = 0; j < table.get(frontRow).size(); j++) {
            if(table.get(frontRow).get(j) != null && table.get(frontRow).get(j).isTank()) {
                System.out.println("Tank present " + table.get(frontRow).get(j).getName());
                return true;
            }
        }
        return false;
    }

    public Deck getPlayerOneDeck() {
        return playerOneDeck;
    }

    public void setPlayerOneDeck(Deck playerOneDeck) {
        this.playerOneDeck = playerOneDeck;
    }

    public Deck getPlayerTwoDeck() {
        return playerTwoDeck;
    }

    public void setPlayerTwoDeck(Deck playerTwoDeck) {
        this.playerTwoDeck = playerTwoDeck;
    }

    public Hero getPlayerOneHero() {
        return playerOneHero;
    }

    public void setPlayerOneHero(Hero playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    public Hero getPlayerTwoHero() {
        return playerTwoHero;
    }

    public void setPlayerTwoHero(Hero playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    public ArrayList<Minion> getPlayerOneHand() {
        return playerOneHand;
    }

    public void setPlayerOneHand(ArrayList<Minion> playerOneHand) {
        this.playerOneHand = playerOneHand;
    }

    public ArrayList<Minion> getPlayerTwoHand() {
        return playerTwoHand;
    }

    public void setPlayerTwoHand(ArrayList<Minion> playerTwoHand) {
        this.playerTwoHand = playerTwoHand;
    }

    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    public void setTable(ArrayList<ArrayList<Minion>> table) {
        this.table = table;
    }
}
