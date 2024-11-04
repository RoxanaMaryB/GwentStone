package game.logic;

import lombok.Getter;
import lombok.Setter;
import fileio.CardInput;

import java.util.ArrayList;

@Getter @Setter
public class GameUtils {
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;
}
