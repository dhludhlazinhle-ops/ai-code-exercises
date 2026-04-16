package za.co.wethinkcode.taskmanager.util;

import za.co.wethinkcode.taskmanager.model.Task;
import za.co.wethinkcode.taskmanager.model.TaskPriority;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.*;

public class TaskTextParser {

    public static Task parseTaskFromText(String text) {
        String title = text;
        TaskPriority priority = TaskPriority.MEDIUM;
        LocalDateTime dueDate = null;
        List<String> tags = new ArrayList<>();

        Matcher pMatcher = Pattern.compile("!(\\d)").matcher(text);
        if (pMatcher.find()) {
            int level = Integer.parseInt(pMatcher.group(1));
            priority = mapPriority(level);
            title = title.replace(pMatcher.group(), "").trim();
        }

        Matcher tMatcher = Pattern.compile("#(\\w+)").matcher(text);
        while (tMatcher.find()) {
            tags.add(tMatcher.group(1));
        }
        title = title.replaceAll("#\\w+", "").trim();

        String lower = text.toLowerCase();

        if (lower.contains("today")) {
            dueDate = LocalDate.now().atTime(23, 59);
            title = title.replace("today", "").trim();
        } else if (lower.contains("tomorrow")) {
            dueDate = LocalDate.now().plusDays(1).atTime(23, 59);
            title = title.replace("tomorrow", "").trim();
        } else if (lower.contains("next")) {
            for (DayOfWeek day : DayOfWeek.values()) {
                if (lower.contains(day.name().toLowerCase())) {
                    dueDate = LocalDate.now()
                            .with(TemporalAdjusters.next(day))
                            .atTime(23, 59);
                    title = title.replaceAll("next\\s+" + day.name().toLowerCase(), "").trim();
                }
            }
        }

        return new Task(title.trim(), priority, dueDate, tags);
    }

    private static TaskPriority mapPriority(int level) {
        switch (level) {
            case 1: return TaskPriority.HIGH;
            case 2: return TaskPriority.MEDIUM;
            case 3: return TaskPriority.LOW;
            default: return TaskPriority.MEDIUM;
        }
    }
}
