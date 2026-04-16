package za.co.wethinkcode.taskmanager.util;

import za.co.wethinkcode.taskmanager.model.Task;

import java.util.*;

public class TaskMergeService {

    public static List<Task> mergeTasks(List<Task> list1, List<Task> list2) {
        Map<String, Task> map = new HashMap<>();

        for (Task t : list1) {
            map.put(t.getTitle(), t);
        }

        for (Task t : list2) {
            map.putIfAbsent(t.getTitle(), t);
        }

        return new ArrayList<>(map.values());
    }
}
