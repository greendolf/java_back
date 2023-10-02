package infrastructure.controllers.endpoint.task;

import app.ILoginService;
import app.ITaskService;
import infrastructure.builder.Builder;
import infrastructure.controllers.endpoint.IController;
import infrastructure.controllers.endpoint.IFactory;
import infrastructure.dtos.Method;
import infrastructure.dtos.Request;
import infrastructure.dtos.Response;

import java.util.HashMap;
import java.util.Map;

public class Factory implements IFactory {
    @Override
    public IController createInstance() {
        return null;
    }
}

class TaskController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return (path.startsWith("/tasks") && (method == Method.GET || method == Method.POST));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, String> requestParams = request.params;
        String login = requestParams.get("login");
        String token = requestParams.get("token");
        ITaskService ts = Builder.buildTaskService();
        boolean result = false;
        if (request.method == Method.GET) {
        } else if (request.method == Method.POST) {
            int value1 = Integer.parseInt(requestParams.get("value1"));
            int value2 = Integer.parseInt(requestParams.get("value2"));
            result = ts.createTask(login, value1, value2);
        }
        Response response = new Response();
        if (result) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = new HashMap<>();
            response.body.put("result", "OK");
            //response.body = "OK";
        } else {
            throw new Exception("error while creating task");
        }
        return response;
    }
}