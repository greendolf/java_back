package infrastructure;

import app.IService;
import app.routeService;

import java.util.HashMap;
import java.util.Map;

public class httpController implements IController {
    @Override
    public String handle(String method, String uri, String params) {
        Map<String, String> args = new HashMap<>();
        if (method.equals("GET")) {
            args.putAll(queryToMap(params));
        } else if (method.equals("POST")) {
            args.putAll(bodyToMap(params));
        }
        IService rs = new routeService();
        return rs.handle(args);
    }

    private Map<String, String> bodyToMap(String json) {
        Map<String, String> result = new HashMap<>();
        String temp = json.substring(1, json.length() - 1);
        String[] pairs = temp.split(",");
        for (String pair : pairs) {
            result.put(pair.split(":")[0].replace("\"", ""), pair.split(":")[1].replace("\"", ""));
        }
        return result;
    }

    private Map<String, String> queryToMap(String query) {
        if (query != null) {
            String[] params = query.split("&");
            Map<String, String> result = new HashMap<>();
            for (String param : params) {
                result.put(param.split("=")[0], param.split("=")[1]);
            }
            return result;
        }
        return null;
    }
}
