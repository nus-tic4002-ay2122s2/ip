package ip.duke.exceptions;

public class DukeException extends Exception {
    public DukeException(String message, Throwable ex) {
        super(message);
    }
}