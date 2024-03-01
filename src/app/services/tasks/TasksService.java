package app.services.tasks;

import app.IStorage;
import app.IStorageUsing;
import app.ITasksService;

import java.util.Arrays;

public class TasksService implements ITasksService, IStorageUsing {
    private IStorage storage;

    @Override
    public String getTasks(String login) {
        return storage.getTasks(login);
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
