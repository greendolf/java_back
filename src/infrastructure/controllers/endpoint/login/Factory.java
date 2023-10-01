package infrastructure.controllers.endpoint.login;


import app.ILoginService;
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
        return new LoginController();
    }
}
class LoginController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return ((path.startsWith("/login")) && (method == Method.GET));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, String> requestParams = request.params;
        String login = requestParams.get("login");
        String password = requestParams.get("password");
        ILoginService ts = Builder.buildLoginService();
        String token = ts.login(login, password);
        Response response = new Response();
        if (token != null) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = token;
        } else {
            throw new Exception("login and/or password are invalid");
        }
        return response;
    }
}
