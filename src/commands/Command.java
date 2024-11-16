package commands;

import fileio.ActionsInput;
import game.logic.GameTable;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Command {
    private ActionsInput action;
    private GameTable table;
    private ArrayNode output;

    public Command(final ActionsInput action, final GameTable table, final ArrayNode output) {
        this.setAction(action);
        this.setTable(table);
        this.setOutput(output);
        CommandContext.getInstance().setAction(action);
        CommandContext.getInstance().setTable(table);
        CommandContext.getInstance().setOutput(output);
    }
}
