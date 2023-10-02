package infrastructure.controllers.endpoint.tasks;

import app.ITasksService;
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
        return new TasksController();
    }
}

class TasksController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return ((path.startsWith("/tasks")) && (method == Method.GET));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, String> requestParams = request.params;
        String login = requestParams.get("login");
        String token = requestParams.get("token");
        ITasksService ts = Builder.buildTasksService();
        String result = ts.getTasks(login);
        Response response = new Response();
        if (result != null) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = new HashMap<>();
            response.body.put("result", "OK");
            //response.body = "OK";
        } else {
            throw new Exception("error while getting tasks");
        }
        return response;
    }
}
