package app;

public interface IStorage {
    public String checkLogPas(String login, String password);
    public String registerNewAcc(String login, String password);
}
