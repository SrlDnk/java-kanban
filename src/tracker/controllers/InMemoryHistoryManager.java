package tracker.controllers;

import tracker.model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        Task taskCopy = new Task(task.getTitle(), task.getDescription());
        taskCopy.setId(task.getId());
        taskCopy.setStatus(task.getStatus());
        history.add(taskCopy);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyCopy = new ArrayList<>();
        for (Task t : history) {
            historyCopy.add(t);
        }
        return historyCopy;
    }
}
