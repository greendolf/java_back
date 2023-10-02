package app.services.tasks;

import app.IStorage;
import app.IStorageUsing;
import app.ITasksService;

import java.util.Arrays;

public class TasksService implements ITasksService, IStorageUsing {
    private IStorage storage;

    @Override
    public String getTasks(String login) {
        StringBuilder result = new StringBuilder();
        String[] tasks = storage.getTasks(login);
        if (tasks == null ) return null;
        for (String task : tasks) {
            result.append(task);
        }
        return result.toString();
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
