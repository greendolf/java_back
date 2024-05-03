package app.services;

import app.api.IStorage;
import app.api.IStorageUsing;
import app.api.ITasksService;

public class TasksService implements ITasksService, IStorageUsing {
    private IStorage storage;

    @Override
    public String getTasks(int id) throws Exception {
        return storage.getTasks(id);
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
