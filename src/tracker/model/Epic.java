package tracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void addSubtaskId(int id) {
        if (!subtasksIds.contains(id)) {
            subtasksIds.add(id);
        }
    }

    public void removeSubtaskId(int id) {
        subtasksIds.remove((Integer) id);
    }

    public void clearSubtasks() {
        subtasksIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtasksIds=" + subtasksIds +
                '}';
    }
}