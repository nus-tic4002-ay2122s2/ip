package ip.duke;

import ip.duke.exceptions.DukeException;
import ip.duke.task.Task;

import java.lang.String;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * LisGenie Chat Bot App - tasks management assistant.
 *
 * </P>Deals with user tasks registry, chat service and administration.
 *
 * <P>Note that tasks are stored as Task objects in a Task[] array (max size: 100).
 *
 * @author Gwee Yeu Chai
 * @version 1.0
 */
public class Duke {
    // TASKS array to store Task objects
    private static final Task[] TASKS = new Task[100];
    // index variable to track each Task stored in array
    private static int index;

    static void greet() {
        System.out.print("Hello! I'm LisGenie");
        System.out.println("What can I do for you?");
    }
    // Exit message
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
                    isBye = echoBye(input);
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                }finally {
                    drawALine();
                }
            } while (!isBye);

            in.close();
    }
    // This method processes tasks and generates dialogues
    private static boolean echoBye(String command) throws DukeException {
        // Exit the program
        if (command.equals("bye")) {
            bye();
            return true;
        } else {
            // Parse user inputs and output the corresponding desired tasks
            String[] words = null;
            // Check multiple words presence in input before splitting into a string array
            if(command.indexOf(" ") > 0){
                words = command.split(" ", 2);
                command = words[0].trim();
            }

            switch (command) {
            case "done":
                try {
                    int itemIndex = Integer.parseInt(words[1].trim()) - 1;

                    if (itemIndex < 0 || itemIndex > 99) {
                        echoOffList(itemIndex);
                    } else {
                        updateDoneStatus(itemIndex);
                    }
                } catch (NumberFormatException ex) {
                    throw new DukeException(echoNotNum("done"), ex);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoTaskNum("done"), err);
                }
                break;
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
                    throw new DukeException(echoNoBy("deadline"), err);
                }
                break;
            case "event":
                try {
                    addEvent(words[1].trim());
                } catch (NullPointerException | ArrayIndexOutOfBoundsException err){
                    throw new DukeException(echoNoAt("event"), err);
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
        TASKS[index] = new Deadline(parts[0].trim(), parts[1].trim());
        echoAdded(TASKS[index]);
        ++index;
    }
    private static void addEvent(String input) {
        String[] parts = input.split(" /at ", 2);
        TASKS[index] = new Event(parts[0].trim(), parts[1].trim());
        echoAdded(TASKS[index]);
        ++index;
    }

    private static void addTodo(String input) {
        TASKS[index] = new Todo(input);
        echoAdded(TASKS[index]);
        ++index;
    }

    private static void updateDoneStatus(int idx){
        Task item = TASKS[idx];

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
        System.out.printf("%13s", " ");
        System.out.printf("[%s][%s] %s%n", input.getId(), input.getStatusIcon(), input);

        int count = index + 1;
        System.out.printf("%11s", " ");
        System.out.printf("Now you have %d %s in the list.%n", count, count == 1 ? "task" : "tasks");
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

    private static String echoNoAt(String task) {
        return String.format("LisGenie : OOPS!!! O %s use: \"event <specify event> /at <datetime>\"", task);
    }

    private static String echoNoBy(String task) {
        return String.format("LisGenie : OOPS!!! O %s use: \"deadline <specify task> /by <datetime>\"", task);
    }
    // This method informs user that no task description is entered
    private static String echoNoDesc(String task) {
        return String.format("LisGenie : OOPS!!! The description of a %s cannot be empty, Master?", task);
    }

    private static void echoNoEntries() {
        System.out.print("LisGenie : ");
        System.out.println("O! Task not in list, Master? Add a task? Retry?");
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
    // This method informs the user an unknown input is entered
    private static void echoNonInput(){
        System.out.print("LisGenie : ");
        System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(| Master?");
    }
}
