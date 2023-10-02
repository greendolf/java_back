package infrastructure.controllers.endpoint.calculation;


import app.ICalculationService;
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
        return new CalculationController();
    }
}
class CalculationController implements IController {

    @Override
    public boolean supports(String path, Method method) {
        return ((path.startsWith("/calculation")) && (method == Method.POST));
    }

    @Override
    public Response service(Request request) throws Exception {
        Map<String, Object> requestBody = request.body;
        int id = (int) requestBody.get("id");
        ICalculationService ts = Builder.buildCalculationService();
        String calc = null;
        if (request.path.endsWith("/sum")) {
            calc = ts.sum(id);
        } else if (request.path.endsWith("/sub")) {
            calc = ts.sub(id);
        }
        Response response = new Response();
        if (calc != null) {
            response.code = 200;
            response.headers = new HashMap<>();
            response.headers.put("Content-Type", "text/plain; charset=UTF-8");
            response.body = new HashMap<>();
            response.body.put("result", calc);
            //response.body = calc;
        } else {
            throw new Exception("calc is not defined");
        }
        return response;
    }
}
