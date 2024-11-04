package game.logic;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import fileio.*;
import cards.*;

@Getter @Setter
public class GameTable {
    // create table of 4x5
    private final int rows = 4;
    private final int columns = 5;
    protected ArrayList<ArrayList<Minion>> table;

    protected Deck playerOneDeck;
    protected Deck playerTwoDeck;

    protected ArrayList<Card> playerOneHand = new ArrayList<>();
    protected ArrayList<Card> playerTwoHand = new ArrayList<>();

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

}
