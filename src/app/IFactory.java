package app;

public interface IFactory {
    IService create();
    public String getPurpose();
}
