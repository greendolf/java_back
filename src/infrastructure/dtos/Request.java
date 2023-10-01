package infrastructure.dtos;

import java.util.Map;


public class Request {
    public Method method;
    public String path;
    public Map<String, String> params;
    public Map<String, String> headers;
    public String body;
}