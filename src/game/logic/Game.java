package game.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.*;
import cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private Input inputData;
    private ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();
    private GameState gameState = new GameState();
    private GameUtils gameUtils = new GameUtils();
    private GameTable table;

    public void startGame(Input inputData, ArrayNode output, ObjectMapper objectMapper) {
        this.inputData = inputData;
        this.output = output;
        this.objectMapper = objectMapper;
        gameState.setNrGames(inputData.getGames().size());
        for(GameInput game : inputData.getGames()) {
            gameState.setGameOver(false);
            gameState.setNrRound(0);

            int playerOneDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();

            ArrayList<CardInput> playerOneDeck = inputData.getPlayerOneDecks().getDecks().get(playerOneDeckIdx);
            ArrayList<CardInput> playerTwoDeck = inputData.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx);

            gameUtils.setPlayerOneDeckIdx(playerOneDeckIdx);
            gameUtils.setPlayerTwoDeckIdx(playerTwoDeckIdx);
            gameUtils.setShuffleSeed(game.getStartGame().getShuffleSeed());

            table = new GameTable();
            table.setPlayerOneDeck(new Deck(playerOneDeck));
            table.setPlayerTwoDeck(new Deck(playerTwoDeck));

            table.setPlayerOneHero((Hero)CardFactory.createCard(game.getStartGame().getPlayerOneHero()));
            table.setPlayerTwoHero((Hero)CardFactory.createCard(game.getStartGame().getPlayerTwoHero()));

            gameUtils.setShuffleSeed(game.getStartGame().getShuffleSeed());
            Collections.shuffle(table.getPlayerOneDeck().getCards(), new Random(gameUtils.getShuffleSeed()));
            Collections.shuffle(table.getPlayerTwoDeck().getCards(), new Random(gameUtils.getShuffleSeed()));
        }
    }
}
