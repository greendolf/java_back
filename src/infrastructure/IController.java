package infrastructure;

public interface IController {
    String handle(String method, String uri, String params);

}
