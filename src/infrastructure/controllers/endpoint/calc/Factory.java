package infrastructure.controllers.endpoint.calc;


import app.ICalcService;
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
        return new CalcController();
    }
}
class CalcController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return ((path.startsWith("/calc")) && (method == Method.GET));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, String> requestParams = request.params;
        int a = Integer.parseInt(requestParams.get("a"));
        int b = Integer.parseInt(requestParams.get("b"));
        ICalcService ts = Builder.buildCalcService();
        String calc = null;
        if (request.path.endsWith("/sum")) {
            calc = ts.sum(a, b);
        } else if (request.path.endsWith("/sub")) {
            calc = ts.sub(a, b);
        }
        Response response = new Response();
        if (calc != null) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = calc;
        } else {
            throw new Exception("calc is not defined");
        }
        return response;
    }
}
