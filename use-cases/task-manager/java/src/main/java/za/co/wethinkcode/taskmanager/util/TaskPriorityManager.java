package za.co.wethinkcode.taskmanager.util;

import za.co.wethinkcode.taskmanager.model.Task;
import za.co.wethinkcode.taskmanager.model.TaskPriority;

import java.util.*;

public class TaskPriorityManager {

    public static List<Task> sortByPriority(List<Task> tasks) {
        tasks.sort((a, b) -> getWeight(b.getPriority()) - getWeight(a.getPriority()));
        return tasks;
    }

    private static int getWeight(TaskPriority priority) {
        switch (priority) {
            case HIGH: return 3;
            case MEDIUM: return 2;
            case LOW: return 1;
            default: return 0;
        }
    }
}
