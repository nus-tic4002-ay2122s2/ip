package storage;

import dateTime.DateTimeDuke;
import task_classes.Task;

import java.util.Vector;

class TaskListEncoder {


    /**
     * The method to convert all the task(s) data to String format and store in Vector<String> format
     *
     * @param list the task list in Vector<Task> format
     * @return all the converted task in Vector<String> format
     */
    static Vector<String> encodeTaskList(Vector<Task> list){

        Vector<String> encodedTaskList = new Vector<>();

        for(Task task : list){
            String encodedTask = encodeSingleTask(task);

            encodedTaskList.add(encodedTask);
        }

        return encodedTaskList;
    }

    /**
     * The method to convert a single task to a single Task in String format
     * @param task the single Task in the task list
     * @return the converted task in String format
     */
    private static String encodeSingleTask(Task task){
        String taskType = task.getType();
        String taskDescription = task.getDescription();
        String taskStatus = task.getStatusIcon();

        if(taskStatus.equals("X")){
            taskStatus = "1";
        } else {
            taskStatus = "0";
        }

        String encodedTask = taskType + " | " + taskStatus + " | " + taskDescription;

        switch(taskType){
            case "E":
                DateTimeDuke starting = task.getStartingTime();
                DateTimeDuke ending = task.getEndingTime();
                String startingLocalDateTime = starting.convertToStringTypeII();
                String endingLocalDateTime = ending.convertToStringTypeII();

                encodedTask = encodedTask + " | " + startingLocalDateTime + " | " + endingLocalDateTime;

                break;
            case "D":
                DateTimeDuke deadlineDateTime = task.getDeadlineTime();
                String deadlineLocalDateTime = deadlineDateTime.convertToStringTypeII();
                encodedTask = encodedTask + " | " + deadlineLocalDateTime;

                break;
        }

        return encodedTask;
    }
}