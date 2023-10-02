package infrastructure.storage.textStorage;

import app.IStorage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Factory {
    private static IStorage instance = null;

    public static IStorage createInstance() {
        if (instance == null) {
            instance = new TextStorage();
        }
        return instance;
    }
}

class TextStorage implements IStorage
{
    public static String dataPath = "C:\\3kurs_fullstack\\data";
    public static String usersFile = "users.txt";

    @Override
    public boolean findUser(String login, String password)
    {
        File file = new File(dataPath + "\\" + usersFile);
        String userInfo = login + " " + password;
        boolean state = false;

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String st;

            while ((st = br.readLine()) != null)
            {
                if (st.equals(userInfo)) state = true;
            }
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }

        return state;
    }

    @Override
    public boolean addUser(String login, String password) {
        return false;
    }

    @Override
    public String[] getTasks(String username)
    {
        List<String> lines = new ArrayList<>();
        File file = new File(dataPath + "\\" + username + ".txt");

        if (!file.exists()) return new String[0];

        try (Scanner sc = new Scanner(file))
        {
            while (sc.hasNextLine()) lines.add(sc.nextLine().strip());
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }

        return lines.toArray(new String[0]);
    }

    @Override
    public boolean createTask(String username, int ID, int value1, int value2)
    {
        File file = new File(dataPath + "\\" + username + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)))
        {
            if (!file.exists()) Files.createFile(Paths.get(dataPath + "\\" + username + ".txt"));
            String[] taskParams = new String[]{String.valueOf(ID), String.valueOf(value1), String.valueOf(value2), "\"X\"", "\"Waiting\"", "\n"};
            writer.write(String.join(" ", taskParams));
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }

        return true;
    }

    @Override
    public boolean modifyTask(String username, int ID, int result, String status)
    {
        try
        {
            Path path = Paths.get(dataPath + "\\" + username + ".txt");
            String[] lines = getTasks(username);

            for (int i = 0; i < lines.length; i++)
            {
                int fileID = Integer.parseInt(lines[i].split(" ")[0]);

                if (fileID == ID && status.equals("Processing"))
                {
                    lines[i] = lines[i].replace("\"Waiting\"", "\"" + status + "\"\n");
                }
                else if (fileID == ID && status.equals("Processed"))
                {
                    lines[i] = lines[i].replace("\"X\"", String.valueOf(result));
                    lines[i] = lines[i].replace("\"Processing\"", "\"" + status + "\"\n");
                }
            }

            String outputLine = String.join("\n", lines);
            Files.writeString(path, outputLine);
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }

        return true;
    }

    @Override
    public Map<String, String> getTaskValues(String username, int ID)
    {
        String[] lines = getTasks(username);
        Map<String, String> values = new HashMap<>();

        for (String line : lines)
        {
            int fileID = Integer.parseInt(line.split(" ")[0]);

            if (fileID == ID)
            {
                String[] lineParams = line.split(" ");
                values.put("value1", lineParams[1]);
                values.put("value2", lineParams[2]);
            }
        }

        return values;
    }
}
