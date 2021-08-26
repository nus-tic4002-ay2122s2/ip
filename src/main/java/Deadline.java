public class Deadline extends Task {
    // Variables
    protected String by;

    // Constructor
    public Deadline(String taskName, String by) {
        super(taskName);
        this.prefix = 'D';
        this.by = by;
    }

    // Getter
    @Override
    public String getTask() {
        return (super.getTask() + " (by: " + by + ")");
    }
}