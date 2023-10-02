package app.services.tasks;

import app.IStorage;
import app.IStorageUsing;
import app.ITasksService;

import java.util.Arrays;

public class TasksService implements ITasksService, IStorageUsing {
    private IStorage storage;

    @Override
    public String getTasks(String login) {
        StringBuilder result = new StringBuilder();
        String[] lines = storage.getTasks(login);

        if (lines.length == 0) return "EMPTY";

        String[][] arr = new String[lines.length][lines[0].split(" ").length];

        for (int i = 0; i < lines.length; i++) {
            arr[i] = lines[i].split(" ");
            String start = (i == 0) ? "[" : "";
            String end = (i == lines.length - 1) ? "]" : ",";
            result.append(start).append(Arrays.toString(arr[i])).append(end);
        }

        return result.toString();
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
