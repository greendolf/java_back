package infrastructure.controllers.endpoint.task;

import app.ITaskService;
import infrastructure.builder.Builder;
import infrastructure.controllers.endpoint.IController;
import infrastructure.controllers.endpoint.IFactory;
import infrastructure.dtos.Method;
import infrastructure.dtos.Request;
import infrastructure.dtos.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Factory implements IFactory {
    @Override
    public IController createInstance() {
        return new TaskController();
    }
}

class TaskController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return (Objects.equals(path, "/task")) && (method == Method.GET || method == Method.POST);
    }

    @Override
    public Response service(Request request) throws Exception {
        ITaskService ts = Builder.buildTaskService();
        int result = -1;
        if (request.method == Method.GET) {
            Map<String, String> requestParams = request.params;
        } else if (request.method == Method.POST) {
            Map<String, Object> requestBody = request.body;
            String login = (String) requestBody.get("login");
            String token = (String) requestBody.get("token");
            int value1 = (int) requestBody.get("value1");
            int value2 = (int) requestBody.get("value2");
            result = ts.createTask(login, value1, value2);
        }
        Response response = new Response();
        if (result != -1) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = new HashMap<>();
            response.body.put("result", result);
            //response.body = "OK";
        } else {
            throw new Exception("error while creating task");
        }
        return response;
    }
}