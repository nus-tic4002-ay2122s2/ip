package duke.storage;
import duke.task.Task;
import duke.ui.Message;

/**
 * Inherent from generic TempList class,
 * TempTaskList constrains that it only stores Task type elements in the list
 * Adding method to print tasks in the list
 * Methods that specifically involve calling method of Task
 */
public class TempTaskList extends TempList<Task> {
    public TempTaskList() {
        super();
    }

    /**
     * print all tasks in the list
     */
    public void print() {
        if (list.size() == 0) {
            Message.emptyList();
        }
        int i = 1;
        for (Task task : list) {
            Message.echo(i + ". " + task.toString());
            i++;
        }
    }

    public int count() {
        return list.size();
    }

    /**
     * Calling this method to print
     * [Undone num / total task num]
     */
    public void tellStats() {
        int numTask = this.count();
        int numDoneTask = 0;
        for (Task task : list) {
            if (task.isDone()) {
                numDoneTask++;
            }
        }
        Message.tellTaskNum(numTask, numDoneTask);
    }

    /**
     * mark done at index
     * @param index
     */
    public void markDoneAt(int index) {
        list.get(index).markDone();
        support.firePropertyChange("list", null, list);
    }

}