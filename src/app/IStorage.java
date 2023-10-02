package app;

import java.util.Map;

public interface IStorage {
    public boolean findUser(String login, String password);
    public boolean addUser(String login, String password);
    public String[] getTasks(String login);
    public int createTask(String login, int value1, int value2);
    public boolean modifyTask(int ID, int result, String status);
    public Map<String, Integer> getTaskValues(int ID);

}
