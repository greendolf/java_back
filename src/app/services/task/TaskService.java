package app.services.task;

import app.IStorage;
import app.IStorageUsing;
import app.ITaskService;

public class TaskService implements ITaskService, IStorageUsing {
    private IStorage storage;
    @Override
    public int createTask(String username, int value1, int value2)
    {
        return storage.createTask(username, value1, value2);
    }


    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
