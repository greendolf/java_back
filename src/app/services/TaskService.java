package app.services;

import app.api.IStorage;
import app.api.IStorageUsing;
import app.api.ITaskService;
import app.dtos.DTO;

public class TaskService implements ITaskService, IStorageUsing {
    private IStorage storage;

    @Override
    public int createTask(DTO data) throws Exception {
        int id = data.id;
        int nx = data.nx;
        int ny = data.ny;
        double time_end = data.time_end;
        double length = data.length;
        double width = data.width;
        double lambda = data.lambda;
        double ro = data.ro;
        double c = data.c;
        double t_start = data.t_start;
        double t_left = data.t_left;
        double t_right = data.t_right;
        return storage.createTask(id, nx, ny, time_end, length, width, lambda, ro, c, t_left, t_right, t_start);
    }

    @Override
    public boolean deleteTask(int id) {
        return storage.deleteTask(id);
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
