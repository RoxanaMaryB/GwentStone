package game.logic;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameState {
    private int nrGamesSoFar;
    private int playerOneWins;
    private int playerTwoWins;
}
