package ip.duke;

import ip.duke.exceptions.DukeException;
import ip.duke.parser.Parser;
import ip.duke.storage.Storage;
import ip.duke.task.Task;
import ip.duke.tasklist.TaskList;
import ip.duke.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
/**
 * LisGenie Chat Bot CLI App - a task management online assistant.
 *
 * </P>Deals with user tasks register, chat service and administration.
 *
 * <P>Note that tasks are stored as Task objects in a Task[] array (Optimum max size : 100).
 *
 * @author Gwee Yeu Chai
 * @version 1.6
 * @since 2021-08-01
 */
public class Duke {
    private static LinkedHashSet<Task> tasks;
    private final static String DATA_PATH = "data/tasks.txt";
    private final static String BACKUP_PATH = "data/backup.txt";

    public Duke(String filePath) {
        Storage storage = new Storage(filePath);
        try {
            tasks = TaskList.load(Storage.getFilePath());
        } catch (DukeException e) {
            Ui.showLoadingError(e);
            tasks = TaskList.getList();
            storage.setFilePath(BACKUP_PATH);
        }
    }

    public void run() {
        // Greets user and prompts user for input
        Ui.greet();
        // Warns user to delete some old records if database full
        if (tasks.size() == 100) {
            Ui.showSizeFull();
        }
        // Gets user input
        Scanner in = new Scanner(System.in);
        String input;
        boolean isBye = false;
        // Tasks and conversations loop
        do {
            Ui.echoUserPrompt();
            input = in.nextLine().trim();
            Ui.drawALine();

            if (input.isEmpty()) {
                // Flushes the buffer
                in.nextLine();
                Ui.echoEmptyInput();
            }

            try {
                isBye = Parser.hasTask(input);
            } catch (DukeException e) {
                Ui.echoDukeErrorMsg(e);
            } finally {
                Ui.drawALine();
            }
        } while (!isBye);

        in.close();
    }

    public static void main(String[] args) {
        File f = new File(DATA_PATH);
        File f_backup = new File(BACKUP_PATH);

        if(!f.exists() && !f_backup.exists()){
            f.getParentFile().mkdirs();
            f_backup.getParentFile().mkdirs();
            try {
                f.createNewFile();
                f_backup.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new Duke(DATA_PATH).run();
    }
}