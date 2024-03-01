package infrastructure.storage.mongoStorage;

import app.IStorage;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.descending;

import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import infrastructure.builder.Production;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class Factory {
    private static IStorage instance = null;

    public static IStorage createInstance() {
        if (instance == null) {
            instance = new MongoStorage();
        }
        return instance;
    }
}

@Production
class MongoStorage implements IStorage {
    private final String uri = "mongodb://localhost:27017";

    private static MongoCollection<Document> getCollection(MongoClient client, String name) {
        MongoDatabase database = client.getDatabase("java_back");
        return database.getCollection(name);
    }

    @Override
    public boolean findUser(String login, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> collection = getCollection(mongoClient, "users");
            Bson andComparison = and(eq("login", login), eq("password", password));
            Document doc = collection.find(andComparison).first();
            if (doc != null) {
                System.out.println(doc.toJson());
                return true;
            } else {
                System.out.println("no matching users found.");
                return false;
            }
        }
    }

    @Override
    public boolean addUser(String login, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> collection = getCollection(mongoClient, "users");
            if (collection.find(eq("login", login)).first() == null) {
                Document doc = new Document("login", login).append("password", password);
                collection.insertOne(doc);
                if (findUser(login, password)) {
                    return true;
                } else {
                    System.out.println("error while registration");
                    return false;
                }
            } else {
                System.out.println("this login is already exist");
                return false;
            }
        }
    }

    @Override
    public String getTasks(String login) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> tasksCol = getCollection(mongoClient, "tasks");
            StringBuilder result = new StringBuilder("[");
            if (tasksCol.find(eq("login", login)).first() != null) {
                MongoCursor<Document> tasks = tasksCol.find(eq("login", login)).projection(excludeId()).iterator();
                while (tasks.hasNext()) {
                    Document task = tasks.next();
                    result.append(task.toJson());
                    if (tasks.hasNext()) {
                        result.append(",");
                    }
                }
                result.append("]");
                return String.valueOf(result);
            } else {
                System.out.println("there is no tasks from this user");
                return null;
            }
        }
    }

    @Override
    public int createTask(String login, int value1, int value2) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> tasks = getCollection(mongoClient, "tasks");
            int id = Objects.requireNonNull(tasks.find().sort(descending("id")).first()).get("id", Integer.class) + 1;
            Document doc = new Document("id", id).append("login", login).append("value1", value1).append("value2", value2).append("result", "null").append("status", "not started");
            tasks.insertOne(doc);
            if (tasks.find(eq("id", id)).first() != null) {
                return id;
            } else {
                System.out.println("error while creating task");
                return -1;
            }
        }
    }

    @Override
    public boolean deleteTask(int id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> tasks = getCollection(mongoClient, "tasks");
            System.out.println("id = " + id);
            if (tasks.find(eq("id", id)).first() != null) {
                tasks.findOneAndDelete(eq("id", id));
                return tasks.find(eq("id", id)).first() == null;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean modifyTask(int id, int result, String status) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> tasksCol = getCollection(mongoClient, "tasks");
            if (tasksCol.find(eq("id", id)).first() != null) {
                Bson updates = Updates.combine(Updates.set("result", result), Updates.set("status", status));
                tasksCol.updateOne(eq("id", id), updates);
                return true;
            } else {
                System.out.println("error while modifying task");
                return false;
            }
        }
    }

    @Override
    public Map<String, Integer> getTaskValues(int id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoCollection<Document> tasksCol = getCollection(mongoClient, "tasks");
            if (tasksCol.find(eq("id", id)).first() != null) {
                Map<String, Integer> result = new HashMap<>();
                Document doc = tasksCol.find(eq("id", id)).first();
                assert doc != null;
                result.put("value1", doc.get("value1", Integer.class));
                result.put("value2", doc.get("value2", Integer.class));
                return result;
            } else {
                System.out.println("error while getting task values");
                return null;
            }
        }
    }
}
