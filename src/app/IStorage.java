package app;

import java.util.Map;

public interface IStorage {
    public boolean findUser(String login, String password);
    public boolean addUser(String login, String password);
    public String[] getTasks(String login);
    public boolean createTask(String login, int ID, int value1, int value2);
    public boolean modifyTask(String login, int ID, int result, String status);
    public Map<String, String> getTaskValues(String login, int ID);

}
