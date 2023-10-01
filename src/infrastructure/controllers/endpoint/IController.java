package infrastructure.controllers.endpoint;

import infrastructure.dtos.Method;
import infrastructure.dtos.Request;
import infrastructure.dtos.Response;

public interface IController {
    boolean supports(String path, Method method);
    Response service(Request request) throws Exception;
}
