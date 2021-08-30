public class AddDeadlineCommand extends Command {
    public AddDeadlineCommand(String fullCommand) {
        super(fullCommand);
    }

    private static void CheckDescription(String fullCommand) throws LackOfDescriptionException {
        if (fullCommand.trim().equals("deadline") ) {
            throw new LackOfDescriptionException();
        }
    }

    private static void CheckDate(String fullCommand, String description) throws LackOfDateException {
        if ( fullCommand.equals("deadline " + description)
                || fullCommand.trim().equals("deadline " + description + " /by") ) {
            throw new LackOfDateException();
        }
    }

    public void run(TaskList taskList) {
        try {
            CheckDescription(fullCommand);
            String description = Parser.description(fullCommand);
            CheckDate(fullCommand, description);
            String date = Parser.date(fullCommand);
            taskList.addDeadline(description, date);
            int size = taskList.size;
            UI.addMessage(taskList.tasks.get(size - 1), size);
        } catch (LackOfDescriptionException e) {
            System.out.println("OOPS!!! Pls key in the description for the task");
        } catch (LackOfDateException e) {
            System.out.println("OOPS!!! Pls key in the date for the task");
        }
    }
}
