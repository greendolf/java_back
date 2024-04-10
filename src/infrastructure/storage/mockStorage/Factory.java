package infrastructure.storage.mockStorage;

import app.IStorage;
import infrastructure.dtos.UserDTO;

import java.util.Map;

public class Factory {
    private static IStorage instance = null;

    public static IStorage createInstance() {
        if (instance == null) {
            instance = new MockStorage();
        }
        return instance;
    }
}

class MockStorage implements IStorage {

    @Override
    public boolean findUser(UserDTO user) {
        return true;
    }

    @Override
    public boolean addUser(UserDTO user) {
        return true;
    }

    @Override
    public String getTasks(String login) {
        return "";
    }

    @Override
    public int createTask(String login, int value1, int value2) {
        return 0;
    }

    @Override
    public boolean deleteTask(int id) {
        return false;
    }

    @Override
    public boolean modifyTask(int ID, int result, String status) {
        return false;
    }

    @Override
    public Map<String, Integer> getTaskValues(int ID) {
        return null;
    }
}
