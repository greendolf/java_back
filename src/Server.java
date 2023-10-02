//mapper

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import infrastructure.controllers.Locator;
import infrastructure.controllers.endpoint.IController;
import infrastructure.controllers.endpoint.IFactory;
import infrastructure.dtos.Method;
import infrastructure.dtos.Request;
import infrastructure.dtos.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new Handler("/"));
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}

class Handler implements HttpHandler {
    private final String rootContext;

    public Handler(String rootContext) {
        this.rootContext = rootContext;
        Router.registerRoutes();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream responseStream = httpExchange.getResponseBody();
        Headers responseHeaders = httpExchange.getResponseHeaders();
        Map<String, Object> responseBody;

        try {
            InputStream requestBodyStream = httpExchange.getRequestBody();
            Map<String, Object> requestBody =
                    new ObjectMapper().readValue(new String(requestBodyStream.readAllBytes()), HashMap.class);
            Headers requestHeaders = httpExchange.getRequestHeaders();
            String requestMethod = httpExchange.getRequestMethod();
            URI requestURI = httpExchange.getRequestURI();

            Request request = new Request();
            request.method = Method.valueOf(requestMethod.toUpperCase());
            request.path = requestURI.getPath();
            request.params = new HashMap<>();
            String query = requestURI.getQuery();
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] kv = param.split("=");
                    request.params.put(kv[0], kv[1]);
                }
            }
            request.headers = new HashMap<>();
            if (requestHeaders != null) {
                if (!requestHeaders.isEmpty()) {
                    for (String key : requestHeaders.keySet()) {
                        request.headers.put(key, requestHeaders.getFirst(key));
                    }
                }
            }
            request.body = requestBody;


            Response response = Router.route(request);
            if (response.headers != null) {
                if (!response.headers.isEmpty()) {
                    for (String key : response.headers.keySet()) {
                        responseHeaders.set(key, response.headers.get(key));
                    }
                }
            }
            responseBody = response.body;
            httpExchange.sendResponseHeaders(response.code, responseBody.toString().getBytes().length);
        } catch (Exception ex) {
            responseBody = new HashMap<>();
            responseBody.put("desc", "fatal error" + ex.getMessage()); //"Fatal error: " + ex.getMessage();
            responseHeaders.clear();
            httpExchange.sendResponseHeaders(500, responseBody.toString().getBytes().length);
        }

        responseStream.write(new ObjectMapper().writeValueAsString(responseBody).getBytes());
        responseStream.flush();
        responseStream.close();
    }
}

class Router {

    private static IFactory[] controllerFactories;

    public static void registerRoutes() {
        controllerFactories = Locator.locate();
    }

    public static Response route(Request request) throws Exception {
        IController controller = null;
        boolean controllerFound = false;
        for (IFactory controllerFactory : controllerFactories) {
            controller = controllerFactory.createInstance();
            if (controller.supports(request.path, request.method)) {
                controllerFound = true;
                break;
            }
        }

        Response response = null;
        if (controllerFound) {
            response = controller.service(request);
        } else {
            response = new Response();
            response.code = 400;
            response.headers = new HashMap<>();
            response.body = new HashMap<>();
            response.body.put("desc", "no implementation for path or method");
            //response.body = "No implementation for path or method";

        }
        return response;
    }
}