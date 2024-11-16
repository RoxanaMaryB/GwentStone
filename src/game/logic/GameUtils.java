package game.logic;

public final class GameUtils {

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

    private static final int MAX_MANA = 10;

    private GameUtils() {
    }

    /**
     * Increase the mana of the player with the given amount.
     * @param mana
     * @param playerIdx
     */
    public static void increaseMana(final int mana, final int playerIdx) {
        if (playerIdx == 1) {
            setPlayerOneMana(Math.min(mana, MAX_MANA) + getPlayerOneMana());
        } else {
            setPlayerTwoMana(Math.min(mana, MAX_MANA + getPlayerTwoMana());
        }
    }

    /**
     * Decrease the mana of the player with the given amount.
     * @param mana
     * @param playerIdx
     */
    public static void decreaseMana(final int mana, final int playerIdx) {
        if (playerIdx == 1) {
            setPlayerOneMana(getPlayerOneMana() - mana);
        } else {
            setPlayerTwoMana(getPlayerTwoMana() - mana);
        }
    }

    public static int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    public static void setPlayerOneDeckIdx(final int playerOneDeckIdx) {
        GameUtils.playerOneDeckIdx = playerOneDeckIdx;
    }

    public static int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    public static void setPlayerTwoDeckIdx(final int playerTwoDeckIdx) {
        GameUtils.playerTwoDeckIdx = playerTwoDeckIdx;
    }

    public static int getShuffleSeed() {
        return shuffleSeed;
    }

    public static void setShuffleSeed(final int shuffleSeed) {
        GameUtils.shuffleSeed = shuffleSeed;
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(final int currentPlayer) {
        GameUtils.currentPlayer = currentPlayer;
    }

    public static int getNrGames() {
        return nrGames;
    }

    public static void setNrGames(final int nrGames) {
        GameUtils.nrGames = nrGames;
    }

    public static int getNrGamesSoFar() {
        return nrGamesSoFar;
    }

    public static void setNrGamesSoFar(final int nrGamesSoFar) {
        GameUtils.nrGamesSoFar = nrGamesSoFar;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void setGameOver(final boolean gameOver) {
        GameUtils.gameOver = gameOver;
    }

    public static int getPlayerOneWins() {
        return playerOneWins;
    }

    public static void setPlayerOneWins(final int playerOneWins) {
        GameUtils.playerOneWins = playerOneWins;
    }

    public static int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public static void setPlayerTwoWins(final int playerTwoWins) {
        GameUtils.playerTwoWins = playerTwoWins;
    }

    public static int getNrTurn() {
        return nrTurn;
    }

    public static void setNrTurn(final int nrTurn) {
        GameUtils.nrTurn = nrTurn;
    }

    public static int getPlayerOneMana() {
        return playerOneMana;
    }

    public static void setPlayerOneMana(final int playerOneMana) {
        GameUtils.playerOneMana = playerOneMana;
    }

    public static int getPlayerTwoMana() {
        return playerTwoMana;
    }

    public static void setPlayerTwoMana(final int playerTwoMana) {
        GameUtils.playerTwoMana = playerTwoMana;
    }
}
