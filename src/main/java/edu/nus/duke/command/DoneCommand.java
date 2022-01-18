package edu.nus.duke.command;

import edu.nus.duke.task.TaskList;

/**
 * Represent done command that extends from Command.
 */
public class DoneCommand extends Command {
    // Variables
    public static final String CMD = "done";
    private int idx;

    // Constructor
    public DoneCommand(int idx) {
        this.idx = idx;
    }

    // Methods
    @Override
    public void run(TaskList taskList) {
        taskList.doneTask(idx);
    }
}