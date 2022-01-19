package edu.nus.duke.command;

import java.time.LocalDate;

import edu.nus.duke.task.TaskList;
import edu.nus.duke.ui.Ui;

/**
 * Represent list command that extends from Command.
 */
public class ListCommand extends Command {
    // Variables
    public static final String CMD = "list";
    private LocalDate dateFilter;

    // Constructor
    public ListCommand() {
    }

    public ListCommand(LocalDate dateFilter) {
        this.dateFilter = dateFilter;
    }

    // Methods
    @Override
    public void run(TaskList taskList) {
        String message = "Here are the tasks in your list:";
        if (dateFilter != null) {
            message = "Here are the filtered tasks in your list:";
        }

        Ui.printMessage(message, false);
        taskList.printTasks(dateFilter, null);
    }
}
