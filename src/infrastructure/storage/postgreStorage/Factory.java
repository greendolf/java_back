package infrastructure.storage.postgreStorage;

import app.IStorage;
import infrastructure.builder.Production;
import infrastructure.dtos.UserDTO;
import infrastructure.storage.entities.UsersEntity;
import jakarta.annotation.Resource;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.*;
import jakarta.transaction.UserTransaction;


import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Factory {
    private static IStorage instance = null;

    @Produces
    public static IStorage createPostgresStorage() {
        if (instance == null) {
            instance = new PostgresStorage();
        }
        return instance;
    }
}

@Production
class PostgresStorage implements IStorage {

    @PersistenceContext(unitName = "java_back")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    /*private Connection getConnectionJDBC() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/test";
            String db_login = "postgres";
            String db_password = "1q2w3e";
            return DriverManager.getConnection(url, db_login, db_password);
        } catch (Exception e) {
            throw new Exception("Exception in getConn()" + e.getMessage());
        }
    }*/

    private Connection getConnectionPool() throws Exception {
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup("jdbc/UserTransaction");
            return ds.getConnection();
        } catch (Exception e) {
            throw new Exception("Exception in getConn() " + e.getMessage());
        }
    }

    @Override
    public boolean findUser(UserDTO user) {
        String login = user.getLogin();
        String password = user.getPassword();
        try {
            /*try (Connection conn = getConnectionPool()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
                st.setString(1, login);
                st.setString(2, password);
                ResultSet rs = st.executeQuery();

                boolean userFound = rs.next();

                rs.close();
                st.close();
                return userFound;
            }*/
            userTransaction.begin();
            entityManager.joinTransaction();
            System.out.println("POSTGRES: TRANSACTION JOINED: " + entityManager.isJoinedToTransaction());
            List<UsersEntity> persons = entityManager.createQuery("SELECT u FROM UsersEntity u WHERE u.login = :login AND u.password = :password", UsersEntity.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getResultList();
            return !persons.isEmpty();
        } catch (Exception e) {
            System.out.println("Error while JDBC operating: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addUser(UserDTO user) throws Exception {
        String login = user.getLogin();
        String password = user.getPassword();
        System.out.println("POSTGRES: ADD USER (" + login + ", " + password + ")");
        try {
            userTransaction.begin();
            entityManager.joinTransaction();

            System.out.println("POSTGRES: TRANSACTION JOINED: " + entityManager.isJoinedToTransaction());

            List<UsersEntity> persons = entityManager.createQuery("SELECT u FROM UsersEntity u WHERE u.login = :login", UsersEntity.class).setParameter("login", login).getResultList();
            //UsersEntity persons = entityManager.find(UsersEntity.class, login);
            System.out.print("POSTGRES: ");
            System.out.println(persons);
            if (persons == null || persons.isEmpty()) {
                UsersEntity userEntity = new UsersEntity();
                userEntity.setLogin(login);
                userEntity.setPassword(password);

                entityManager.persist(userEntity);
                userTransaction.commit();
                System.out.println("POSTGRES: USER REGISTERED");
                return true;
            } else return false;

        } catch (Exception e) {
            throw new Exception("Error while JPA operating: " + e.getMessage());
        }
    }

    @Override
    public String getTasks(String login) {
        try {
            try (Connection conn = getConnectionPool()) {
                PreparedStatement st = conn.prepareStatement("SELECT * FROM tasks WHERE login = ?");
                st.setString(1, login);
                ResultSet rs = st.executeQuery();
                StringBuilder resultBuilder = new StringBuilder("[");
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int value1 = rs.getInt("value1");
                        int value2 = rs.getInt("value2");
                        String result = rs.getString("result");
                        String status = rs.getString("status");
                        resultBuilder.append("{\"id\":").append(id).append(", \"value1\":").append(value1).append(", \"value2\":").append(value2).append(", \"result\":").append(result).append(", \"status\":\"").append(status).append("\"},");
                    }
                    // отрезаем последний элемнт строки
                    // resultString = StringUtils.chop(resultString);
                    resultBuilder.deleteCharAt(resultBuilder.length() - 1);
                }
                resultBuilder.append("]}");
                System.out.println(resultBuilder);
                st.close();
                System.out.println("GET SUCCESS");
                return String.valueOf(resultBuilder);
            }
        } catch (Exception e) {
            System.out.println("Error while JDBC operating: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int createTask(String login, int value1, int value2) {
        try {
            try (Connection conn = getConnectionPool()) {
                int id = new BigDecimal(new Date().getTime() / 100 % 1000000000).intValueExact();

                PreparedStatement st = conn.prepareStatement("INSERT INTO tasks (id, login, value1, value2, status) VALUES (?, ?, ?, ?, ?)");

                st.setInt(1, id);
                st.setString(2, login);
                st.setInt(3, value1);
                st.setInt(4, value2);
                st.setString(5, "not started");

                st.executeUpdate();
                st.close();
                return id;
            }
        } catch (Exception e) {
            System.out.println("Error while createTask: " + e.getMessage());
        }
        return 404;
    }

    @Override
    public boolean modifyTask(int id, int result, String status) {
        try {
            try (Connection conn = getConnectionPool()) {
                PreparedStatement st = conn.prepareStatement("UPDATE tasks SET result = ?, status = ? WHERE id = ?");

                st.setInt(1, result);
                st.setString(2, status);
                st.setInt(3, id);

                st.executeUpdate();
                st.close();

                return true;
            }
        } catch (Exception e) {
            System.out.println("Error while JDBC operating: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Map<String, Integer> getTaskValues(int ID) {
        return null;
    }

    @Override
    public boolean deleteTask(int id) {
        try {
            try (Connection conn = getConnectionPool()) {
                PreparedStatement st = conn.prepareStatement("DELETE FROM tasks WHERE id = ?");

                st.setInt(1, id);

                st.executeUpdate();
                st.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error while JDBC operating: " + e.getMessage());
        }
        return false;
    }

}
