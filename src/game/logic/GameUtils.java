package game.logic;

public class GameUtils {

    private static int playerOneDeckIdx;
    private static int playerTwoDeckIdx;
    private static int shuffleSeed;

    private static int currentPlayer;

    private static int nrGames;
    private static int nrGamesSoFar;
    private static int nrTurn;
    private static boolean gameOver;

    private static int playerOneMana;
    private static int playerTwoMana;
    private static int playerOneWins;
    private static int playerTwoWins;

    public static void increaseMana(int mana, int playerIdx) {
        if (playerIdx == 1) {
            setPlayerOneMana(Math.min(mana, 10) + getPlayerOneMana());
        } else {
            setPlayerTwoMana(Math.min(mana, 10) + getPlayerTwoMana());
        }
    }

    public static void decreaseMana(int mana, int playerIdx) {
        if (playerIdx == 1) {
            setPlayerOneMana(getPlayerOneMana() - mana);
        } else {
            setPlayerTwoMana(getPlayerTwoMana() - mana);
        }
    }

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

    public static int getNrGamesSoFar() {
        return nrGamesSoFar;
    }

    public static void setNrGamesSoFar(int nrGamesSoFar) {
        GameUtils.nrGamesSoFar = nrGamesSoFar;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void setGameOver(boolean gameOver) {
        GameUtils.gameOver = gameOver;
    }

    public static int getPlayerOneWins() {
        return playerOneWins;
    }

    public static void setPlayerOneWins(int playerOneWins) {
        GameUtils.playerOneWins = playerOneWins;
    }

    public static int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public static void setPlayerTwoWins(int playerTwoWins) {
        GameUtils.playerTwoWins = playerTwoWins;
    }

    public static int getNrTurn() {
        return nrTurn;
    }

    public static void setNrTurn(int nrTurn) {
        GameUtils.nrTurn = nrTurn;
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
