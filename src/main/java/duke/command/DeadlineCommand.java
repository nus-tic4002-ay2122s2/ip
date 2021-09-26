package duke.command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import duke.dukeexception.DukeException;
import duke.parser.Parser;
import duke.ui.Ui;
import duke.task.TaskList;
import duke.storage.Storage;
import duke.task.Deadline;

public class DeadlineCommand extends Command{

    /**
     * Constructs the Deadlines Command
     * @param taskDes the Command the User input
     */
    public DeadlineCommand(String taskDes){
        super(taskDes);
    }

    /**
     * Execute the deadline command by creating the Deadlines task
     * @param tasks the task list
     * @param ui the Ui
     * @param storage the Storage
     * @throws DukeException any expected error
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try{
            commandInstruction.substring(9);
        }
        catch(StringIndexOutOfBoundsException e){
            throw new DukeException("Deadline command can't be empty");
        }
        if (!commandInstruction.contains(" /by ")) {
            throw new DukeException("Please state /by yyyy-mm-dd");
        }
        int dividerPosition2 = commandInstruction.indexOf(" /by ");
        String taskDes = commandInstruction.substring(9, dividerPosition2);
        String taskDateTime = commandInstruction.substring(dividerPosition2 + 5);
        Deadline deadline = new Deadline(taskDes, taskDateTime);
        tasks.addTask(deadline);
        storage.save(tasks);
    }

    /**
     * Checking if the user has input any time and adding it into the class if he has
     * @param taskDes the task description
     * @param taskDateTime the task date and time
     * @return the events class
     * @throws DukeException any expected error
     */
    public static Deadline deadlineTimeSetter(String taskDes, String taskDateTime) throws DukeException {
        try{
            if (!taskDateTime.contains(" ")) {
                LocalDate d1 = Parser.convertStringToDate(taskDateTime);
                return new Deadline(taskDes, d1);
            } else {
                int dividerPosition3 = taskDateTime.indexOf(" ");
                String taskDate = taskDateTime.split(" ")[0];
                String taskTime = taskDateTime.substring(dividerPosition3 + 1);
                LocalDate d1 = Parser.convertStringToDate(taskDate);
                LocalTime t1 = Parser.convertStringToTime(taskTime);
                return new Deadline(taskDes, d1, t1);

            }
        }catch (DateTimeParseException e){
            throw new DukeException("Please set date as YYYY-MM-DD");
        }
    }

}
