package ip.duke;

import ip.duke.exceptions.DukeException;
import ip.duke.task.Task;

import java.lang.String;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * LisGenie Chat Bot CLI App - a task management online assistant.
 *
 * </P>Deals with user tasks register, chat service and administration.
 *
 * <P>Note that tasks are stored as Task objects in a Task[] array (Optimum max size : 100).
 *
 * @author Gwee Yeu Chai
 * @version 1.0
 * @since 2021-08-01
 */
public class Duke {
    // Collection used to preserve input sequence, yield constant time ops
    private static final LinkedHashSet<Task> TASKS = new LinkedHashSet<>(100);
    // greet and prompt user for input
    static void greet() {
        System.out.print("Hello! I'm LisGenie");
        System.out.println("What can I do for you?");
    }
    // Bye / exit message
    static void bye() {
        System.out.print("LisGenie : ");
        System.out.printf("Bye. Hope to see you again soon!%n");
    }

    // This method draws a horizontal line
    static void drawALine() {
        System.out.print("          ");
        Stream.generate(() -> "_").limit(65).forEach(System.out::print);
        System.out.println();
    }

    public static void main(String[] args) {
        // Greeting screen display
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        // Greet user
        greet();
        // Get user input
            Scanner in = new Scanner(System.in);
            String input;
            boolean isBye = false;
            // conversions loop
            do {
                System.out.printf("%nMasterOm : ");
                input = in.nextLine().trim();
                drawALine();

                if (input.isEmpty()) {
                    // flush buffer
                    in.nextLine();
                    echoEmptyInput();
                }

                try {
                    isBye = hasTask(input);
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }finally {
                    drawALine();
                }
            } while (!isBye);

            in.close();
    }
    /**
     * This is the method which processes tasks and generates related dialogues.
     * @param command Text input by user.
     * @return A boolean value.
     * @exception DukeException On input error.
     * @see DukeException
     */
    private static boolean hasTask(String command) throws DukeException {
        // Exit the program
        if (command.equals("bye")) {
            bye();
            return true;
        } else {
            // Parse user inputs to run the selected tasks errands
            String[] words = null;
            // Check if multiple words exist in input before splitting into a string array
            if(command.indexOf(" ") > 0){
                words = command.split(" ", 2);
                command = words[0];
            }

            switch (command) {
            case "list":
                echoList();
                break;
            case "todo":
                try {
                    addTodo(words[1].trim());
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoDesc("todo"), err);
                }
                break;
            case "deadline":
                try {
                    addDeadline(words[1].trim());
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoBy(), err);
                }
                break;
            case "event":
                try {
                    addEvent(words[1].trim());
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoAt(), err);
                }
                break;
            case "delete":
                try {
                    doDelete(words[1]);
                } catch (NumberFormatException ex) {
                    throw new DukeException(echoNotNum("delete"), ex);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoTaskNum("delete"), err);
                }
                break;
            case "done":
                try {
                    doDone(words[1]);
                } catch (NumberFormatException ex) {
                    throw new DukeException(echoNotNum("done"), ex);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoTaskNum("done"), err);
                }
                break;
            case "":
                break;
            default:
                echoNonInput();
                break;
            }
        }
        return false;
    }

    private static void addDeadline(String input) {
        String[] parts = input.split(" /by ", 2);
        Deadline item = new Deadline(parts[0].trim(), parts[1].trim());
        TASKS.add(item);
        echoAdded(item);
    }

    private static void addEvent(String input) {
        String[] parts = input.split(" /at ", 2);
        Event item = new Event(parts[0].trim(), parts[1].trim());
        TASKS.add(item);
        echoAdded(item);
    }

    private static void addTodo(String input) {
        Todo item = new Todo(input);
        TASKS.add(item);
        echoAdded(item);
    }
    /**
     * Directly deletes a task from TASKS storage
     * by index / ranked order position.
     * @param idx Index to the stored task.
     */
    private static void delete(int idx){
        Task item = getItem(idx);

        if(item != null) {
            TASKS.remove(item);
            echoDelete(item);
        }else{
            echoNoEntries();
        }
    }

    private static void doDelete(String word) {
        int itemIndex = Integer.parseInt(word.trim()) - 1;

        if (itemIndex < 0 || itemIndex > 99) {
            echoOffList(itemIndex);
        } else {
            delete(itemIndex);
        }
    }

    private static void doDone(String word) {
        int itemIndex = Integer.parseInt(word.trim()) - 1;

        if (itemIndex < 0 || itemIndex > 99) {
            echoOffList(itemIndex);
        } else {
            updateDoneStatus(itemIndex);
        }
    }
    /**
     * Locate target item in TASKS storage
     * by iterative search.
     * @param idx Index of the item to get
     * @return A Task Object.
     */
    private static Task getItem(int idx) {
        int currentIndex =0;
        Task task = null;

        for (Task element : TASKS) {
            if (currentIndex == idx) {
                task = element;
                break;
            } else {
                currentIndex++;
            }
        }

        return task;
    }

    private static void updateDoneStatus(int idx){
        Task item = getItem(idx);

        if(item != null) {
            item.setDone();
            echoDone(item);
        }else{
            echoNoEntries();
        }
    }

    private static void echoAdded(Task input) {
        System.out.print("LisGenie : ");
        System.out.println("Got it. I've added this task:");
        postUpdate(input);
    }

    private static void echoDelete(Task item) {
        System.out.print("LisGenie : ");
        System.out.println("Noted. I've removed this task:");
        postUpdate(item);
    }

    private static void echoDone(Task item) {
        System.out.print("LisGenie : ");
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("%13s", " ");
        System.out.printf("[%s] %s%n", item.getStatusIcon(), item);
    }

    private static void echoEmptyInput() {
        System.out.print("LisGenie : ");
        System.out.println("eh...Om! Empty string to skip here, O Master! Retry again?");
    }

    private static void echoList() {
        System.out.print("LisGenie : ");
        System.out.printf("Here are the tasks in your list:%n");

        int i = 1;
        for (Task s : TASKS) {
            if (s != null)
                System.out.printf("%12d.[%s][%s] %s%n", i++,s.getId(), s.getStatusIcon(), s);
        }
    }

    private static String echoNoAt() {
        return String.format("LisGenie : OOPS!!! O %s use: \"event <specify event> /at <datetime>\"", "event");
    }

    private static String echoNoBy() {
        return String.format("LisGenie : OOPS!!! O %s use: \"deadline <specify task> /by <datetime>\"", "deadline");
    }
    // This method informs user that no task description is entered
    private static String echoNoDesc(String task) {
        return String.format("LisGenie : OOPS!!! The description of a %s cannot be empty, Master?", task);
    }

    private static void echoNoEntries() {
        System.out.print("LisGenie : ");
        System.out.println("O! Task not in list, Master? Add a task? Retry?");
    }
    // This method informs the user an unknown or invalid input is entered
    private static void echoNonInput(){
        System.out.print("LisGenie : ");
        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(| Master?");
    }

    private static String echoNoTaskNum(String task) {
        return String.format("LisGenie : O? Master, forgot to enter the Task number after '%s'?", task);
    }

    private static String echoNotNum(String task) {
        return String.format("LisGenie : O Master, use digit(s) only for Task number after '%s'! Om!", task);
    }

    private static void echoOffList(int idx) {
        System.out.print("LisGenie : ");
        System.out.println("Item position outside of list (1 - 100): " + (idx+1) + " Omm??");
    }
    /**
     * Prints out the task details
     * post-execution, with current size of TASKS.
     * @param input A Task object
     * concerned.
     */
    private static void postUpdate(Task input) {
        System.out.printf("%13s", " ");
        System.out.printf("[%s][%s] %s%n", input.getId(), input.getStatusIcon(), input);

        int count = TASKS.size();
        System.out.printf("%11s", " ");
        System.out.printf("Now you have %d %s in the list.%n", count, count == 1 ? "task" : "tasks");
    }
}
