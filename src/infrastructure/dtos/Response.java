package infrastructure.dtos;

import java.util.Map;


public class Response {
    public int code;
    public Map<String, String> headers;
    public Map<String, Object> body;
}
