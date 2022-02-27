package Commands;
import Ui.*;
import Storage.*;
import TaskList.*;

public class addByeCommand extends Command{

    public static final String COMMAND = "bye";

    public addByeCommand(boolean exit){
        super(false);
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage){
        ui.showGoodBye();
        return null;
    }
}