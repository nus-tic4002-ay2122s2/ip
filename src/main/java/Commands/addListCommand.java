package Commands;

import Storage.Storage;
import TaskList.TaskList;
import Ui.Ui;

import java.io.UnsupportedEncodingException;

public class addListCommand extends Command {
    public static final String COMMAND = "list";

    public addListCommand(String input){
        super(input);
    }

    @Override
    public String execute(TaskList tasklist, Ui ui, Storage storage) throws UnsupportedEncodingException {
        ui.displayTaskList(description, tasklist);
        return null;
    }
}