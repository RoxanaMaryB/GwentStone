package game.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import commands.Command;
import commands.CommandFactory;
import fileio.*;
import cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private Input inputData;
    private ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();
    private GameTable table;

    public void startGame(Input inputData, ArrayNode output, ObjectMapper objectMapper) {
        System.out.println("Starting game...............................");
        this.inputData = inputData;
        this.output = output;
        this.objectMapper = objectMapper;
        GameUtils.setNrGames(inputData.getGames().size());
        GameUtils.setNrGamesSoFar(0);

        for(GameInput game : inputData.getGames()) {
            GameUtils.setGameOver(false);
            GameUtils.setNrTurn(0);
            GameUtils.setCurrentPlayer(game.getStartGame().getStartingPlayer());

            int playerOneDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();

            ArrayList<CardInput> playerOneDeck = inputData.getPlayerOneDecks().getDecks().get(playerOneDeckIdx);
            ArrayList<CardInput> playerTwoDeck = inputData.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx);

            GameUtils.setPlayerOneDeckIdx(playerOneDeckIdx);
            GameUtils.setPlayerTwoDeckIdx(playerTwoDeckIdx);

            table = new GameTable();
            table.setPlayerOneDeck(new Deck(playerOneDeck));
            table.setPlayerTwoDeck(new Deck(playerTwoDeck));

            table.setPlayerOneHero((Hero)CardFactory.createCard(game.getStartGame().getPlayerOneHero()));
            table.setPlayerTwoHero((Hero)CardFactory.createCard(game.getStartGame().getPlayerTwoHero()));

            GameUtils.setPlayerOneMana(1);
            GameUtils.setPlayerTwoMana(1);

            GameUtils.setShuffleSeed(game.getStartGame().getShuffleSeed());
            Collections.shuffle(table.getPlayerOneDeck().getMinions(), new Random(GameUtils.getShuffleSeed()));
            Collections.shuffle(table.getPlayerTwoDeck().getMinions(), new Random(GameUtils.getShuffleSeed()));

            table.addMinionToHand(1);
            table.addMinionToHand(2);

            CommandFactory commandFactory = new CommandFactory();
            ArrayList<ActionsInput> actions = game.getActions();
            for (ActionsInput action : actions) {
                if(GameUtils.isGameOver()) {
                    continue;
                }
                // Command factory
                Command command = new Command(action, table, output);
                commandFactory.executeCommand(command);
            }
        }
    }
}
