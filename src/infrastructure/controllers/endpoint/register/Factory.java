package infrastructure.controllers.endpoint.register;


import app.ICalcService;
import app.ILoginService;
import app.IRegisterService;
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
        return new RegisterController();
    }
}

class RegisterController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return ((path.startsWith("/register")) && (method == Method.POST));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, Object> requestBody = request.body;
        String login = (String) requestBody.get("login");
        String password = (String) requestBody.get("password");
        IRegisterService ts = Builder.buildRegisterService();
        String token = ts.register(login, password);
        Response response = new Response();
        if (token != null) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = new HashMap<>();
            response.body.put("result", token);
            //response.body = token;
        } else {
            throw new Exception("login and/or password are invalid or busy");
        }
        return response;
    }
}