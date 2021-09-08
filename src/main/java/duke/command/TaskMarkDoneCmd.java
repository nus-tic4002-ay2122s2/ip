package duke.command;

import duke.storage.TempTaskList;
import duke.task.Task;
import duke.ui.Message;

/**
 * A Concrete Command in Command Pattern
 */
public class TaskMarkDoneCmd implements UndoableCommand {
    // Task is a Receiver Class in this Command
    private TempTaskList list;
    private Task task;

    public TaskMarkDoneCmd( TempTaskList list) {
        this.list = list;
    }

    @Override
    public void run(String[] args) {
       // list.stream().filter(e -> e)
        for (String arg : args) {
            try {
                task = (Task) list.get(Integer.parseInt(arg) - 1);
                task.markDone();
            } catch (Exception e) {
                Message.echo(Message.exceptionInvalidArgs());
            }
        }
    }

    @Override
    public void undo() {
        task.markUnDone();
    }

    @Override
    public void redo() {

    }
}