package command;

import constant.ErrorMessage;
import parser.CommandParser;
import storage.Storage;
import ui.Ui;
import taskList.TaskList;
import exception.ErrorHandler;
import task.Deadline;
import task.Event;
import task.Todo;


public abstract class Command {
    protected boolean isExit = false;

    /**
     * @return boolean to decide whether the program should be terminated
     */
    protected boolean getIsExit() {return this.isExit;}

    protected void saveData(Storage storage, TaskList taskList) throws ErrorHandler {
        String [] data = taskList.getFileDataFormatList();
        storage.saveData(data);
    }

    public abstract void execute ( Storage storage, Ui ui, TaskList taskList) throws ErrorHandler;


//    /**
//     * @param list Store which contains all user data
//     * @param userInput a string that user key in from the terminal
//     * @throws ErrorHandler customized error
//     */
//    private void process (TaskList list, String userInput) throws ErrorHandler {
//        try {
//            CommandParser parser = new CommandParser(userInput);
//            System.out.println(parser.getCommandWord());
//            switch (parser.getCommandWord()) {
//                case BYE:
//                    Ui.bye();
//                    list.saveData();
//                    this.isExit = true;
//                    break;
//                case LIST:
//                    Ui.printList(list.getSerializedList());
//                    break;
//                case DONE:
//                    int index =Integer.parseInt(parser.getContent());
//                    if(index > 0 && index <= list.getList().size()) {
//                        list.getList().get(index - 1).setStatus(true);
//                        list.saveData();
//                        Ui.printMarkedDone(list.getSerializedList().get(index - 1));
//                    } else {
//                        throw new ErrorHandler("In Command, " + ErrorMessage.INVALID_TASK_NUMBER);
//                    }
//                    break;
//                case DELETE:
//                    int deleteIndex =Integer.parseInt(parser.getContent());
//                    if(deleteIndex > 0 && deleteIndex <= list.getList().size()) {
//                        String deletedItem = list.getSerializedList().get(deleteIndex - 1);
//                        list.removeItem(deleteIndex - 1);
//                        list.saveData();
//                        Ui.printDeletedItem(deletedItem, list.getList().size());
//                    } else {
//                        throw new ErrorHandler("In Command, " + ErrorMessage.INVALID_TASK_NUMBER);
//                    }
//                    break;
//                case TODO:
//                    Todo addedTodo = new Todo(parser.getContent(), false);
//                    list.addItem(addedTodo);
//                    Ui.printTask(addedTodo.toString(), list.getList().size());
//                    break;
//                case EVENT:
//                    Event addedEvent = new Event(parser.getContent(), parser.getAt(), false);
//                    list.addItem(addedEvent);
//                    Ui.printTask(addedEvent.toString(), list.getList().size());
//                    break;
//                case DEADLINE:
//                    Deadline addDeadline = new Deadline(parser.getContent(), parser.getBy(), false);
//                    list.addItem(addDeadline);
//                    Ui.printTask(addDeadline.toString(), list.getList().size());
//                    break;
//                default:
//                    break;
//            }
//        } catch (ErrorHandler e) {
//            throw new ErrorHandler(e.getMessage());
//        }

//    }

}
