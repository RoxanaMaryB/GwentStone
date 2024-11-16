package commands;

import fileio.ActionsInput;
import game.logic.GameTable;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class CommandContext {
    private static CommandContext instance;
    private ActionsInput action;
    private GameTable table;
    private ArrayNode output;

    private CommandContext() { }

    /**
     * Singleton pattern
     * @return the instance of the CommandContext
     */
    public static CommandContext getInstance() {
        if (instance == null) {
            instance = new CommandContext();
        }
        return instance;
    }
}
