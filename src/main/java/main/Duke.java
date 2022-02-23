package main;

import javafx.stage.Stage;
import main.parsers.ParserText;
import main.taskLists.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

//*
// Individual Project for TIC4002 2022 Jan-May
//
// *//
public class Duke {

    public static ArrayList<Task> Tasks = new ArrayList<>();

    public static void main(String[] args) throws DukeException, IOException {

        Storage storage = new Storage("C:\\Users\\yrall\\Documents\\duke\\src\\data\\current_tasks.txt");
        ParserText response = new ParserText();


        UI interaction = new UI();

        interaction.welcome(storage.start());


        while (response.isTrue) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            response.parsetext(input);
        }

        storage.save();

    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input, Stage stage) {
        boolean isExit;
        String response = "";
        response = "TOOLBOX";
        return response;
    }


}