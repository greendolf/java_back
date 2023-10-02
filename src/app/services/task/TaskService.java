package app.services.task;

import app.IStorage;
import app.IStorageUsing;
import app.ITaskService;

public class TaskService implements ITaskService, IStorageUsing {
    private IStorage storage;
    private static int ID = 0;

    @Override
    public boolean createTask(String username, int value1, int value2)
    {
        ID++;
        return storage.createTask(username, ID, value1, value2);
    }


    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
