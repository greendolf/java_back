package infrastructure.controllers.endpoint.auth;


import app.IAuthService;
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
        return new AuthController();
    }
}
class AuthController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return ((path.startsWith("/auth")) && (method == Method.GET));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, String> requestParams = request.params;
        String login = requestParams.get("login");
        String password = requestParams.get("password");
        IAuthService ts = Builder.buildAuthService();
        String token = ts.login(login, password);
        Response response = new Response();
        if (token != null) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = new HashMap<>();
            response.body.put("token", "token");
            //response.body = token;
        } else {
            throw new Exception("login and/or password are invalid");
        }
        return response;
    }
}
