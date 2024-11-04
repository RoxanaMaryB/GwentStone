package game.logic;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.util.ArrayList;
import fileio.*;
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
            for(int j = 0; j < columns; j++) {
                row.add(null);
            }
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
        if(playerIdx == 1) {
            playerOneHand.add(playerOneDeck.getMinions().get(0));
            playerOneDeck.getMinions().remove(0);
        } else {
            playerTwoHand.add(playerTwoDeck.getMinions().get(0));
            playerTwoDeck.getMinions().remove(0);
        }
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
