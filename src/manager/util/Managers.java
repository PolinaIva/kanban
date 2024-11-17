package manager.util;

import manager.HistoryManager;
import manager.TaskManager;
import manager.impl.InMemoryHistoryManager;
import manager.impl.InMemoryTaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
