package app;

public interface ITaskService {
    public int createTask(String username, int value1, int value2);
    public boolean deleteTask(int id);
}
