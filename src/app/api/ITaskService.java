package app.api;

import app.dtos.DTO;

public interface ITaskService {
    public int createTask(DTO data) throws Exception;
    public boolean deleteTask(int id);
}
