package game.logic;

import fileio.CardInput;

import java.util.ArrayList;

public class GameUtils {
    private static int playerOneDeckIdx;
    private static int playerTwoDeckIdx;
    private static int shuffleSeed;
    private static int playerTurn;
    private static int currentPlayer;
    private static int nrGames;
    private static int nrRound;
    private static boolean gameOver;
    private static int playerOneMana;
    private static int playerTwoMana;

    public static int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    public static void setPlayerOneDeckIdx(int playerOneDeckIdx) {
        GameUtils.playerOneDeckIdx = playerOneDeckIdx;
    }

    public static int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    public static void setPlayerTwoDeckIdx(int playerTwoDeckIdx) {
        GameUtils.playerTwoDeckIdx = playerTwoDeckIdx;
    }

    public static int getShuffleSeed() {
        return shuffleSeed;
    }

    public static void setShuffleSeed(int shuffleSeed) {
        GameUtils.shuffleSeed = shuffleSeed;
    }

    public static int getPlayerTurn() {
        return playerTurn;
    }

    public static void setPlayerTurn(int playerTurn) {
        GameUtils.playerTurn = playerTurn;
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(int currentPlayer) {
        GameUtils.currentPlayer = currentPlayer;
    }

    public static int getNrGames() {
        return nrGames;
    }

    public static void setNrGames(int nrGames) {
        GameUtils.nrGames = nrGames;
    }

    public static int getNrRound() {
        return nrRound;
    }

    public static void setNrRound(int nrRound) {
        GameUtils.nrRound = nrRound;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void setGameOver(boolean gameOver) {
        GameUtils.gameOver = gameOver;
    }

    public static int getPlayerOneMana() {
        return playerOneMana;
    }

    public static void setPlayerOneMana(int playerOneMana) {
        GameUtils.playerOneMana = playerOneMana;
    }

    public static int getPlayerTwoMana() {
        return playerTwoMana;
    }

    public static void setPlayerTwoMana(int playerTwoMana) {
        GameUtils.playerTwoMana = playerTwoMana;
    }
}
