package game.logic;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameState {
    private int nrGames;
    private int nrRound;
    private boolean gameOver;
}
