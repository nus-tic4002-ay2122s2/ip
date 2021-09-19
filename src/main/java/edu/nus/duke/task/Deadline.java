package edu.nus.duke.task;

/**
 * Represent a deadline with due that extends from Task.
 */
public class Deadline extends Task {
    // Variables
    protected String by;

    // Constructor
    public Deadline(String taskName, String by) {
        super('D', taskName);
        this.by = by;
    }

    public Deadline(String taskName, String by, boolean isDone) {
        this(taskName, by);
        this.isDone = isDone;
    }

    // Getter
    @Override
    public String getTask() {
        return (super.getTask() + " (by: " + by + ")");
    }

    @Override
    public String printForSave() {
        return (super.printForSave() + SAVE_SEP + by);
    }
}
