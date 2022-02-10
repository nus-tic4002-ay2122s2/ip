package duke;

import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores the message to interact with user.
 * */
public class UI {
    /**
     * Prints welcome message of duke.
     * */
    public static void welcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|";
        System.out.println("Hello from\n" + logo);
        splitLine();
        System.out.println("Hello! I'm duke.Duke");
        System.out.println("What can I do for you?");
        splitLine();
    }

    /**
     * Prints add message of duke.
     * @param task task to print after adding to list.
     * @param size size of task list.
     * */
    public static void addMessage(Task task, int size) {
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Prints list message of task list.
     * @param tasks task list for printing.
     * */
    public static void listMessage(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + "." + tasks.get(i).toString());
        }
    }

    /**
     * Prints done message of duke.
     * @param tasks task list.
     * @param index index of task in task list which will be marked as done.
     * */
    public static void doneMessage(ArrayList<Task> tasks, int index) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(index).toString());
    }

    /**
     * Prints delete message of duke.
     * @param task task deleted.
     * @param size number of the tasks remained.
     * */
    public static void deleteMessage(Task task, int size) {
        System.out.println("Noted. I've removed this task: ");
        System.out.println("  "  + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Prints find message of duke.
     * @param tasks task list.
     * @param findResult list stores all the indexes of matched tasks.
     * */
    public static void findMessage(ArrayList<Task> tasks, ArrayList<Integer> findResult) {
        if (findResult.size() == 0) {
            System.out.println("Oops!!! None of task can be found");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int index : findResult) {
                System.out.println(index + 1 + "." + tasks.get(index).toString());
            }
        }
    }

    /**
     * Prints bye message of duke.
     * */
    public static void byeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints out split line.
     * */
    public static void splitLine() {
        System.out.println("--------------------------------------------------------");
    }

    /**
     * Scan and reads the input of user.
     * @return user full command in string.
     * */
    public static String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}