package manager.impl;

import entity.Task;
import manager.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> hisoryList = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (hisoryList.size() == 10) {
            hisoryList.remove(0);
        }
        hisoryList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return hisoryList;
    }
}
