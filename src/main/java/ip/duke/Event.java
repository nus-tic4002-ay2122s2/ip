package ip.duke;

public class Event extends Deadline {

    protected Event(String description, String at) {
        super(description, at);
        setId("E");
    }

    @Override
    public String toString() {
        return String.format("%s (at: %s)", getDescription(), getBy());
    }
}