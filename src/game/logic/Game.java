package game.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import commands.Command;
import commands.CommandOrganizer;
import fileio.Input;
import fileio.GameInput;
import fileio.CardInput;
import fileio.ActionsInput;
import cards.Deck;
import cards.Hero;
import cards.CardFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {
    private Input inputData;
    private ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();
    private GameTable table;

    /**
     * Makes configurations for the game and starts it
     * For each game in the input data, sets a new table, shuffles the decks,
     * sets the heroes and the starting mana
     * For each action in the game, creates a command and executes it
     * @param inData
     * @param out
     * @param objMapper
     */
    public void startGame(final Input inData, final ArrayNode out,
                          final ObjectMapper objMapper) {
        this.inputData = inData;
        this.output = out;
        this.objectMapper = objMapper;
        GameUtils.setNrGames(inputData.getGames().size());
        GameUtils.setNrGamesSoFar(0);
        GameUtils.setPlayerOneWins(0);
        GameUtils.setPlayerTwoWins(0);

        for (GameInput game : inputData.getGames()) {
            GameUtils.setGameOver(false);
            GameUtils.setNrTurn(0);
            GameUtils.setCurrentPlayer(game.getStartGame().getStartingPlayer());

            int playerOneDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();

            ArrayList<CardInput> playerOneDeck = inputData.getPlayerOneDecks()
                    .getDecks().get(playerOneDeckIdx);
            ArrayList<CardInput> playerTwoDeck = inputData.getPlayerTwoDecks()
                    .getDecks().get(playerTwoDeckIdx);

            GameUtils.setPlayerOneDeckIdx(playerOneDeckIdx);
            GameUtils.setPlayerTwoDeckIdx(playerTwoDeckIdx);

            table = new GameTable();
            table.setPlayerOneDeck(new Deck(playerOneDeck));
            table.setPlayerTwoDeck(new Deck(playerTwoDeck));

            table.setPlayerOneHero((Hero) CardFactory
                    .createCard(game.getStartGame().getPlayerOneHero()));
            table.setPlayerTwoHero((Hero) CardFactory
                    .createCard(game.getStartGame().getPlayerTwoHero()));

            GameUtils.setPlayerOneMana(1);
            GameUtils.setPlayerTwoMana(1);

            GameUtils.setShuffleSeed(game.getStartGame().getShuffleSeed());
            Collections.shuffle(table.getPlayerOneDeck().getMinions(),
                    new Random(GameUtils.getShuffleSeed()));
            Collections.shuffle(table.getPlayerTwoDeck().getMinions(),
                    new Random(GameUtils.getShuffleSeed()));

            table.addMinionToHand(1);
            table.addMinionToHand(2);

            CommandOrganizer commandFactory = new CommandOrganizer();
            ArrayList<ActionsInput> actions = game.getActions();
            for (ActionsInput action : actions) {
                Command command = new Command(action, table, output);
                commandFactory.executeCommand(command);
            }
        }
    }
}
