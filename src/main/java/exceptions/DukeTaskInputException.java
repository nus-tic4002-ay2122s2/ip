package exceptions;

import ui.Output_On_Screen;

public class DukeTaskInputException extends Exception {

    private static String errorType;

    public DukeTaskInputException(String errorTypeInput){
        errorType = errorTypeInput;
    }

    /**
     * To get the error type
     *
     * @return the error type
     */
    public static String getErrorType(){
        return errorType;
    }

    /**
     * To print the error message if user try to reach any task in empty task list
     */
    public static void toPrintListIsEmtpyError(){
        System.out.println("     ☹ OOPS!!! The Task List is empty.");
    }

    public static void toPrintCommandCreateError() {
        System.out.println("     ☹ OOPS!!! The Command you just input was in wrong format.");
    }

    /**
     * To print the error message for the wrong input format
     */
    public static void formatWrong(){
        Output_On_Screen.toPrintSeparateLine();
        System.out.println("     \u2639 OOPS!!! The input format wrong, please try again. :-(");
    }

    /**
     * To print the error message if the first word of user input is not in the detection list:
     */
    public static void invalidFirstWordInput(){
        Output_On_Screen.toPrintSeparateLine();
        System.out.println("     \u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        Output_On_Screen.toPrintSeparateLine();
    }
}
