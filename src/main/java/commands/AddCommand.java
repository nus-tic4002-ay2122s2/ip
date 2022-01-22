package commands;


import exceptions.DukeDateTimeError;
import exceptions.DukeTaskInputException;
import storage.Storage;
import task_classes.*;
import parser.Parser;
import ui.Ui;

import java.util.Vector;

public class AddCommand extends Command{

    private String type;
    private String[] inputWords;
    private String description;

    /**
     * The method to initialize AddCommand
     *
     * @param type the task type
     * @param inputWords the entire input in String[] type
     * @throws DukeTaskInputException handle all the errors during user input
     */
    public AddCommand(String type, String[] inputWords) throws DukeTaskInputException {
        this.type = type;
        this.inputWords = inputWords;
        this.description = Parser.toExtractDescription(inputWords);
    }

    /**
     * To add Todo type task to the task list
     * The output (message print onto screen) will be included in this method
     *
     * @param list the entire task list
     */
    private void addTodoTask(Vector<Task> list){

        Todo inputTask = new Todo (description);
        list.add(inputTask);

        Ui.printTodoAddedOutput(inputTask, list.size());
    }

    /**
     * To add Deadline type task to the task list
     * The output (message print onto screen) will be included in this method after task added
     *
     * @param list the entire task list
     */
    private void addDeadlineTask (Vector<Task> list) throws DukeTaskInputException, DukeDateTimeError {

        String date = Parser.toExtractDate(inputWords);

        Deadline newTask = new Deadline(description, false, date);

        list.add(newTask);

        Ui.printDeadlineAddedOutput(newTask, list.size());
    }

    /**
     * To add Event type task to the task list
     * The output (message print onto screen) will be included in this method after task added
     *
     * @param list the entire task list
     */
    private void addEventTask (Vector<Task> list) throws DukeTaskInputException, DukeDateTimeError {

        String date = Parser.toExtractDate(inputWords);
        String startingDateTime = Parser.extractStartingDateTime(date);
        String endingDateTime = Parser.extractEndingDateTime(date);


        Event newTask = new Event(description, false, startingDateTime, endingDateTime);

        list.add(newTask);

        Ui.printEventAddedOutput(newTask, list.size());
    }

    /**
     * The method to execute command
     *
     * @param taskList contain all the task
     * @param ui Ui class
     * @param storage Storage class
     * @throws DukeTaskInputException throw all errors about input command
     * @throws DukeDateTimeError throw all errors about date and time
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeTaskInputException, DukeDateTimeError {
        Vector<Task> list = taskList.getVectorList();

        switch (type) {
            case "todo":
                addTodoTask(list);
                break;
            case "deadline":
                addDeadlineTask(list);
                break;
            case "event":
                addEventTask(list);
                break;
        }
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