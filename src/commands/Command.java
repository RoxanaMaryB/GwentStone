package commands;

import fileio.*;
import game.logic.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Command {
    private ActionsInput action;
    private GameTable table;
    private ArrayNode output;

    public Command(ActionsInput action, GameTable table, ArrayNode output) {
        this.setAction(action);
        this.setTable(table);
        this.setOutput(output);
    }
}
