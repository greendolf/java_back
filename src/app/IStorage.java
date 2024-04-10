package app;

import infrastructure.dtos.UserDTO;

import java.util.Map;

public interface IStorage {
    public boolean findUser(UserDTO user);
    public boolean addUser(UserDTO user) throws Exception;
    public String getTasks(String login);
    public int createTask(String login, int value1, int value2);
    public boolean deleteTask(int id);
    public boolean modifyTask(int ID, int result, String status);
    public Map<String, Integer> getTaskValues(int ID);

}
