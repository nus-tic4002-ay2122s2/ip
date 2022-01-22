package commands;

import exceptions.DukeTaskInputException;
import storage.Storage;
import task_classes.TaskList;
import task_classes.Task;
import ui.Ui;


public class DeleteCommand extends Command {

    private int index;

    /**
     * To initialize DeleteCommand
     *
     * @param index the index number of the task in the task list, not the index in Vector<Task>
     */
    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    /**
     * The method to execute command
     *
     * @param taskList contain all the task
     * @param ui Ui class
     * @param storage Storage class
     * @throws DukeTaskInputException throw all errors about input command
     */
    @Override
    public void execute (TaskList taskList, Ui ui, Storage storage) throws DukeTaskInputException {
        if(taskList.isEmpty()){
            throw new DukeTaskInputException("listIsEmpty");
        }

        Task deletedTask = taskList.getTask(index);
        taskList.deleteTask(index);
        Ui.toPrintTaskDeletedMessage(deletedTask, taskList.size());
    }

    /**
     * The method to let system know whether the command is to exit the Duke
     * @return return false, the program continue
     */
    @Override
    public boolean isExit() {
        return false;
    }
}